import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AdminService } from 'src/app/services/admin.service';
import { CoursBBE } from 'src/app/model/coursBBE';
import { FormGroup, FormsModule } from '@angular/forms';
import { DOCUMENT } from '@angular/common';
import * as fileSaver from 'file-saver';


declare const myTest: any;


@Component({
  selector: 'app-cours-bbe',
  templateUrl: './cours-bbe.component.html',
  styleUrls: ['./cours-bbe.component.css']
})
export class CoursBbeComponent implements OnInit {

  coursList: Array<CoursBBE>;
  date:Date;
  datestring:string;
  dataSource: MatTableDataSource<CoursBBE> = new MatTableDataSource();
  displayedColumns: string[] = ['achatClientele', 'venteClientele', 'midBAM', 'achatClienteleCAL', 'venteClienteleCAL', 'achatinterBAM', 'venteinterBAM', 'rachatinter', 'venteinter', 'rachatsousdel', 'libDevise', 'uniteDevise', 'date'];
  registerForm : FormGroup;
  hideattributes: boolean;

  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(@Inject(DOCUMENT) private document: Document, private adminService: AdminService) { }

 
	ngOnInit() {
    this.hideattributes=true;
  }

  ngAfterViewInit(){
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  getcoursList(value){
    this.date=value
    this.hideattributes=true;
    this.adminService.getcoursbbe(this.date).subscribe(data => {
      this.coursList = data;
      this.dataSource.data = data;
      console.log(this.date);
    });
  }

  generateBBE(){
    this.adminService.generateBBE(this.date).subscribe(data => {
      this.dataSource.data = data;
      console.log(this.date);
    });
    this.hideattributes=false;
  }

  DownloadCSVFile(value) {
    this.date=value;
    this.datestring=value;
    let datefichier = this.datestring.substring(8,10) + this.datestring.substring(5,7)+ this.datestring.substring(0,4) ;
    this.adminService.DownloadCSVFile(this.date).subscribe(response => {
			let blob:any = new Blob([response], { type: 'text/csv; charset=utf-8' });
      fileSaver.saveAs(blob, 'COURBE_BBE_'+datefichier+'.csv');
      console.log('Fichier CSV Cours de Billet téléchargé avec succès !');
		},err => {
      console.log('Erreur lors du téléchargement de fichier!');     
    });
  }

  DownloadEXFile(value) {
    this.date=value;
    this.datestring=value;
    let datefichier = this.datestring.substring(8,10) + this.datestring.substring(5,7)+ this.datestring.substring(0,4) ;
    this.adminService.DownloadEXFile(this.date).subscribe(response => {
			let blob:any = new Blob([response], { type: 'text/octet-stream; charset=utf-8' });
      fileSaver.saveAs(blob, 'COURBE_BBE_'+datefichier+'.xlsx');
      console.log('Fichier Excel Cours de Billet téléchargé avec succès !');
		},err => {
      console.log('Erreur lors du téléchargement de fichier !');     
    });
  }

  DownloadBAMFile(value) {
    this.date=value;
    this.datestring=value;
    let datefichier = this.datestring.substring(8,10) + this.datestring.substring(5,7)+ this.datestring.substring(0,4) ;
    this.adminService.DownloadBAMFile(this.date).subscribe(response => {
			let blob:any = new Blob([response], { type: 'text/txt; charset=utf-8' });
      fileSaver.saveAs(blob, 'BAMFX03_'+datefichier+'.txt');
      console.log('Fichier BAMFX03 téléchargé avec succès !');
		},err => {
      console.log('Erreur lors du téléchargement de fichier !');     
    });
  }

  DownloadWAFAFile(value) {
    this.date=value;
    this.datestring=value;
    let datefichier = this.datestring.substring(8,10) + this.datestring.substring(5,7)+ this.datestring.substring(0,4) ;
    this.adminService.DownloadWAFAFile(this.date).subscribe(response => {
			let blob:any = new Blob([response], { type: 'text/txt; charset=utf-8' });
      fileSaver.saveAs(blob, 'WAFACASH_'+datefichier+'.txt');
      console.log('Fichier WAFACASH téléchargé avec succès !');
		},err => {
      console.log('Erreur lors du téléchargement de fichier !');     
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}




  



