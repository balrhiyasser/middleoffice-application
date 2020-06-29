import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {Router} from "@angular/router";
import { AuthenticationService } from 'src/app/services/authentication.service';
import { AdminService } from 'src/app/services/admin.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: User = new User();
  selectedUser: User = new User(); 
  userList: Array<User>;
  dataSource: MatTableDataSource<User> = new MatTableDataSource();
  errorMessage: string;
  infoMessage: string;
  show: boolean;


  username: string;

  constructor(private authService: AuthenticationService, private adminService: AdminService, private router: Router) {
    this.username=this.authService.username;
   }

  ngOnInit() {
    this.show=false;
    this.findUserDetails(this.username);
  }


  findUserDetails(username){
    
    this.adminService.findUserDetails(this.username).subscribe(data => {
      console.log(data);
      this.user = data;
      this.dataSource.data = data;
    });
  }

  editUserRequest(user: User) {
    this.selectedUser = user;
    this.show=true;
  }

  editUser(){
    this.adminService.updateUser(this.selectedUser).subscribe(data => {
      console.log(data);
      //let itemIndex = this.userList.findIndex(item => item.id == this.selectedUser.id);
      //this.userList[itemIndex] = this.selectedUser;
      //this.dataSource = new MatTableDataSource(this.userList);
      //console.log(this.dataSource);
      this.infoMessage = "Informations are updated successfully !";
      setTimeout(() => {
        window.location.reload();
      },1500 );
    },err => {
        this.errorMessage = "Username should be unique for each user !";
        setTimeout(() => {
          window.location.reload();
        },1500 ); 
    });
  }

  logOut(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
