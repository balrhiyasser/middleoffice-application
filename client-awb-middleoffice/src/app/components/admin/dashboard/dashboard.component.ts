import { Component, OnInit } from '@angular/core';
import {AdminService} from '../../../services/admin.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  userCount:any = "";
  

  constructor(private adminService: AdminService, private authService: AuthenticationService) { }

  ngOnInit() {
    if(this.isAdmin())
    this.numberOfUsers();
  }

  isAdmin(){
    return this.authService.isAdmin();
  }

  numberOfUsers(){
    this.adminService.numberOfUsers().subscribe(data => {
      this.userCount = data.response;
    });
  }

}
