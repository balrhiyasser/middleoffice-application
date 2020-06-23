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
  displayedColumns: string[] = ['id', 'name', 'username', 'action'];
  selectedUser: User = new User();
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
    this.adminService.updateUser(this.selectedUser).subscribe(data => {
      console.log('salam');
      let itemIndex = this.userList.findIndex(item => item.id == this.selectedUser.id);
      this.userList[itemIndex] = this.selectedUser;
      this.dataSource = new MatTableDataSource(this.userList);
      this.infoMessage = "Informations are updated successfully !";
      $("#userModal").modal('hide');
    },err => {
        this.errorMessage = "Username should be unique for each user !";
        $("#userModal").modal('hide');
        setTimeout(() => {
          window.location.reload();
        },2000 );
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
      this.infoMessage = "User deleted successfully !";
      $("#deleteModal").modal('hide');
    },err => {
      this.errorMessage = "Unexpected error occurred !";
    });
  }

}
