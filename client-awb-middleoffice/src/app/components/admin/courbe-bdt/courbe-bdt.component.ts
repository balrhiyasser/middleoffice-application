import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { AdminService } from 'src/app/services/admin.service';
import { CourbeBDT } from 'src/app/model/courbeBDT';

@Component({
  selector: 'app-courbe-bdt',
  templateUrl: './courbe-bdt.component.html',
  styleUrls: ['./courbe-bdt.component.css']
})
export class CourbeBdtComponent implements OnInit {

  courbeList: Array<CourbeBDT>;
  dateCourbe:Date;
  dataSource: MatTableDataSource<CourbeBDT> = new MatTableDataSource();
  displayedColumns: string[] = ['dateEcheance', 'dateValeur', 'dateCourbe', 'tmp', 'volume'];
  registerForm : FormGroup

  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private adminService: AdminService) { }

  /** Life Cycle hook to initialize values */
	ngOnInit() {}

  ngAfterViewInit(){
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  getcourbeList(value){
    this.dateCourbe=value
    this.adminService.getcourbebdt(this.dateCourbe).subscribe(data => {
      this.courbeList = data;
      this.dataSource.data = data;
      console.log(this.dateCourbe);
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
