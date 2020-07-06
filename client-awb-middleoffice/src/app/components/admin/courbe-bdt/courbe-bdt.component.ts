import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { AdminService } from 'src/app/services/admin.service';
import { CourbeBDT } from 'src/app/model/courbeBDT';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { TauxTMPJJ } from 'src/app/model/tmpjj';
import * as fileSaver from 'file-saver';

declare var $: any;

@Component({
  selector: 'app-courbe-bdt',
  templateUrl: './courbe-bdt.component.html',
  styleUrls: ['./courbe-bdt.component.css']
})
export class CourbeBdtComponent implements OnInit {

  courbeList: Array<CourbeBDT>;
  dateCourbe:Date;
  dateCourbestring:string;
  dataSource: MatTableDataSource<CourbeBDT> = new MatTableDataSource();
  dataSourceST: MatTableDataSource<CourbeBDT> = new MatTableDataSource();
  dataSourceLT: MatTableDataSource<CourbeBDT> = new MatTableDataSource();


  displayedColumns: string[] = ['dateEcheance', 'dateValeur', 'maturite', 'dateCourbe', 'tmp', 'volume'];
  displayedColumnsST: string[] = ['dateCourbe', 'maturite', 'taux'];
  displayedColumnsLT: string[] = ['dateCourbe', 'maturite', 'taux'];


  registerForm : FormGroup
  show: boolean // affichage du champ 'maturité'
  mode: boolean // affichage du bouton 'consulter' et 'extraire'
  showgenerate: boolean // affichage du bouton 'générer'
  isViewable: boolean

  taux: string ;
  dateTaux: string ;
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private adminService: AdminService,private authService: AuthenticationService, private toastr: ToastrService) { }

  
	ngOnInit() {
    this.show=true; // le champ maturité n'est pas affiché
    this.mode=false;
    this.showgenerate=false;
    this.isViewable=false;
  }

  ngAfterViewInit(){
    this.dataSource.sort = this.sort;
    this.dataSourceST.sort = this.sort;
    this.dataSourceLT.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  getcourbeList(value){
    this.dateCourbe=value;
    this.adminService.getcourbebdt(this.dateCourbe).subscribe(data => {
      this.courbeList = data;
      this.show=true;
      this.mode=false; 
      this.showgenerate=true;
      this.isViewable=true;
      this.dataSource.data = data;
    });
  }

  generateBDT(value){
    this.dateCourbe=value
    this.adminService.generateBDT(this.dateCourbe).subscribe(data => {
      this.courbeList = data;
      this.dataSource.data = data;
      this.show=false;
      this.mode=true;
      this.isViewable=true;
      console.log(this.dateCourbe);
    });
  }

  generateCourbeST(value){
    this.dateCourbe=value;
    this.adminService.generateCourbeST(this.dateCourbe).subscribe(data => {
      this.dataSourceST.data = data;
      console.log(data);
      this.show=false;
    },err => {
      console.log('Erreur ! '+err);     
    });
    if(this.authService.jwtToken){
      this.toastr.info('Traitement en cours ...','Courbe de taux ST',
      {timeOut: 4500,
      progressBar : true,
      progressAnimation:'increasing'});
    }
    setTimeout(() => {
      $("#courbest").modal('show');    
    },5000 ); 
  }

  generateCourbeLT(value){
    this.dateCourbe=value
    this.adminService.generateCourbeLT(this.dateCourbe).subscribe(data => {
      this.dataSourceLT.data = data;
      this.show=false;
    },err => {
      console.log('Erreur ! '+err);     
    });
    if(this.authService.jwtToken){
      this.toastr.info('Traitement en cours ...','Courbe de taux LT',
      {timeOut: 4500,
      progressBar : true,
      progressAnimation:'increasing'});
    }

    setTimeout(() => {
      $("#courbelt").modal('show');    
    },5000 );
    
  }

  generateTaux(value){
    this.dateCourbe=value
    this.adminService.generateTaux(this.dateCourbe).subscribe(data => {
      console.log(data);
      this.dateTaux = data.dateTaux;
      this.taux = data.taux
      this.show=false;
    });
    if(this.authService.jwtToken){
      this.toastr.info('Traitement en cours ...','Taux moyen pondéré',
      {timeOut: 3000,
      progressBar : true,
      progressAnimation:'increasing'});
    }

    setTimeout(() => {
      $("#tmpjj").modal('show');     
    },3500 );
      
  }

  DownloadSTFile(value) {
    this.dateCourbe=value;
    this.dateCourbestring=value;
    let datefichier = this.dateCourbestring.substring(8,10) + this.dateCourbestring.substring(5,7)+ this.dateCourbestring.substring(0,4) ;  
    this.adminService.DownloadSTFile(this.dateCourbe).subscribe(response => {
			let blob:any = new Blob([response], { type: 'text/csv; charset=utf-8' });
      fileSaver.saveAs(blob, 'COURBE_MS_ST_'+datefichier+'.csv');
      console.log('Fichier Courbe de Taux ST téléchargé avec succès!');
		},err => {
      console.log('Erreur lors du téléchargement de fichier!');     
    });
  }

  DownloadLTFile(value) {
    this.dateCourbe=value;
    this.dateCourbestring=value;
    let datefichier = this.dateCourbestring.substring(8,10) + this.dateCourbestring.substring(5,7)+ this.dateCourbestring.substring(0,4) ;  
    this.adminService.DownloadLTFile(this.dateCourbe).subscribe(response => {
			let blob:any = new Blob([response], { type: 'text/csv; charset=utf-8' });
      fileSaver.saveAs(blob, 'COURBE_MS_LT_'+datefichier+'.csv');
      console.log('Fichier Courbe de Taux LT téléchargé avec succès !');
		},err => {
      console.log('Erreur lors du téléchargement de fichier !');     
    });
  }

  DownloadTMPFile(value) {
    this.dateCourbe=value;
    this.dateCourbestring=value;
    let datefichier = this.dateCourbestring.substring(8,10) + this.dateCourbestring.substring(5,7)+ this.dateCourbestring.substring(0,4) ;  
    this.adminService.DownloadTMPFile(this.dateCourbe).subscribe(response => {
			let blob:any = new Blob([response], { type: 'text/csv; charset=utf-8' });
      fileSaver.saveAs(blob, 'TMPJJ'+datefichier+'.csv');
      console.log('Fichier Taux moyen pondéré téléchargé avec succès!');
		},err => {
      console.log('Erreur lors du téléchargement de fichier !');     
    });
  }
}
