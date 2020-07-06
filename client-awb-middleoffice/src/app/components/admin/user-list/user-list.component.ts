import { Component, OnInit, ViewChild } from '@angular/core';
import {AdminService} from '../../../services/admin.service';
import {User} from '../../../model/user';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

declare var $: any;

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  userList: Array<User>;
  dataSource: MatTableDataSource<User> = new MatTableDataSource();
  displayedColumns: string[] = ['name', 'username', 'email', 'action'];
  selectedUser: User = new User();
  user: User = new User();
  errorMessage: string;
  infoMessage: string;


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.findAllUsers();
  }

  ngAfterViewInit(){
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  findAllUsers(){
    this.adminService.findAllUsers().subscribe(data => {
      this.userList = data;
      this.dataSource.data = data;
    });
  }


/*=============================================*/

  editUserRequest(user: User) {
    this.selectedUser = user;
    $("#userModal").modal('show');
  }

  editUser(){
    console.log('salam');
    this.adminService.updateUser(this.selectedUser).subscribe(data => {
      console.log('salam');
      let itemIndex = this.userList.findIndex(item => item.id == this.selectedUser.id);
      this.userList[itemIndex] = this.selectedUser;
      this.dataSource = new MatTableDataSource(this.userList);
      this.infoMessage = "Les informations sont modifiées avec succès !";
      $("#userModal").modal('hide');
    },err => {
        this.errorMessage = "L'identifiant de l'utilisateur doit être unique !";
        $("#userModal").modal('hide');
        setTimeout(() => {
          window.location.reload();
        },2000 );
    });
  }

  /*=============================================*/

  addUserRequest() {
    $("#createuserModal").modal('show');
  }

  createUser(){
    this.adminService.register(this.user).subscribe(data => {
      console.log('salam');
      //let itemIndex = this.userList.findIndex(item => item.id == this.selectedUser.id);
      //this.userList[itemIndex] = this.selectedUser;
      //this.dataSource = new MatTableDataSource(this.userList);
      this.infoMessage = "L'utilisateur est ajouté avec succès !";
      $("#createuserModal").modal('hide');
      setTimeout(() => {
        window.location.reload();
      },2000 );
    },err => {
        this.errorMessage = "L'utilisateur ajouté existe déjà !";
        $("#createuserModal").modal('hide');
    });
  }

  /*=============================================*/

  deleteUserRequest(user: User) {
    this.selectedUser = user;
    $("#deleteModal").modal('show');
  }

  deleteUser(){
    this.adminService.deleteUser(this.selectedUser).subscribe(data => {
      let itemIndex = this.userList.findIndex(item => item.id == this.selectedUser.id);
      if(itemIndex !== -1){
        this.userList.splice(itemIndex, 1);
      }
      this.dataSource = new MatTableDataSource(this.userList);
      this.infoMessage = "Utilisateur supprimé avec succès !";
      $("#deleteModal").modal('hide');
    },err => {
      this.errorMessage = "Une erreur inattendue s'est produite !";
    });
  }

}
