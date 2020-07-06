import { Component, OnInit, ViewChild } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Parameter } from 'src/app/model/parameter';

declare var $: any;

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  parametersList: Array<Parameter>;
  dataSource: MatTableDataSource<Parameter> = new MatTableDataSource();
  displayedColumns: string[] = ['cle', 'valeur', 'action'];
  selectedParameter: Parameter = new Parameter();
  errorMessage: string;
  infoMessage: string;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.findAllParameters();
  }

  ngAfterViewInit(){
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  findAllParameters(){
    this.adminService.findAllParameters().subscribe(data => {
      this.parametersList = data;
      this.dataSource.data = data;
    });
  }

  createNewParameterRequest(){
    this.selectedParameter = new Parameter();
    $('#parameterModal').modal('show');
  }

  editParameterRequest(parameter: Parameter){
    this.selectedParameter = parameter;
    $('#parameterModal').modal('show');
  }

  saveParameter(){
    if(!this.selectedParameter.id){
      this.createParameter();
    }else{
      this.updateParameter();
    }
  }

  createParameter(){
    this.adminService.createParameter(this.selectedParameter).subscribe(data => {
      this.parametersList.push(data);
      this.dataSource = new MatTableDataSource(this.parametersList);
      this.infoMessage = "Le paramètre est ajouté avec succès !";
      $('#parameterModal').modal('hide');
    },err => {
      $('#parameterModal').modal('hide');
      this.errorMessage = "Une erreur inattendue s'est produite !";
    });
  }

  updateParameter(){
    this.adminService.updateParameter(this.selectedParameter).subscribe(data => {
      let itemIndex = this.parametersList.findIndex(item => item.id == this.selectedParameter.id);
      this.parametersList[itemIndex] = this.selectedParameter;
      this.dataSource = new MatTableDataSource(this.parametersList);
      this.infoMessage = "Le paramètre est mise à jour avec succès !";
      $('#parameterModal').modal('hide');
    },err => {
      this.errorMessage = "Une erreur inattendue s'est produite !";
    });
  }

  deleteParameterRequest(parameter: Parameter){
    this.selectedParameter = parameter;
    $('#deleteModal').modal('show');
  }

  deleteParameter(){
    this.adminService.deleteParameter(this.selectedParameter).subscribe(data => {
      let itemIndex = this.parametersList.findIndex(item => item.id == this.selectedParameter.id);
      if(itemIndex !== -1){
        this.parametersList.splice(itemIndex, 1);
      }
      this.dataSource = new MatTableDataSource(this.parametersList);
      this.infoMessage = "Paramètre supprimé avec succès!";
      $('#deleteModal').modal('hide');
    },err => {
      this.errorMessage = "Une erreur inattendue s'est produite !";
    });
  }

}
