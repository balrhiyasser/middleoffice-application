import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AdminService } from 'src/app/services/admin.service';

import { CoursBBE } from 'src/app/model/coursBBE';
import { FormGroup, FormsModule } from '@angular/forms';
import { DOCUMENT } from '@angular/common';


declare const myTest: any;


@Component({
  selector: 'app-cours-bbe',
  templateUrl: './cours-bbe.component.html',
  styleUrls: ['./cours-bbe.component.css']
})
export class CoursBbeComponent implements OnInit {

  coursList: Array<CoursBBE>;
  date:Date;
  dataSource: MatTableDataSource<CoursBBE> = new MatTableDataSource();
  displayedColumns: string[] = ['achatClientele', 'venteClientele', 'midBAM', 'achatClienteleCAL', 'venteClienteleCAL', 'achatinterBAM', 'venteinterBAM', 'rachatinter', 'venteinter', 'rachatsousdel', 'libDevise', 'uniteDevise', 'date'];
  registerForm : FormGroup

  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(@Inject(DOCUMENT) private document: Document, private adminService: AdminService) { }

  /** Life Cycle hook to initialize values */
	ngOnInit() {}

  ngAfterViewInit(){
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  getcoursList(value){
    this.date=value
    this.adminService.getcoursbbe(this.date).subscribe(data => {
      this.coursList = data;
      this.dataSource.data = data;
      console.log(this.date);
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
/*
  exporttoEXCEL(): void {
      this.document.location.href = 'http://localhost:8080/download/coursbbe.xlsx?date='+this.date; 
  }

  exporttoCSV(): void {
    this.document.location.href = 'http://localhost:8080/download/coursbbe.csv?date='+this.date; 
  }

  exporttoTXTBAM(): void {
    this.document.location.href = 'http://localhost:8080/download/BAMFX03.txt?date='+this.date;
  }

  exporttoTXTWAFA(): void {
    this.document.location.href = 'http://localhost:8080/download/WAFACASH.txt?date='+this.date; 
  } */

}




  



