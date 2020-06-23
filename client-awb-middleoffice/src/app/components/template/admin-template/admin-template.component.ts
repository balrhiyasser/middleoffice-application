import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../services/user.service';
import {Router} from '@angular/router';
import {User} from "../../../model/user";
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-admin-template',
  templateUrl: './admin-template.component.html',
  styleUrls: ['./admin-template.component.css']
})
export class AdminTemplateComponent implements OnInit {
  username: string;
  mode: any;
  role: any;

  constructor(private authService: AuthenticationService, private router: Router) {
    this.username=this.authService.username;
    this.role=this.authService.roles;
  }

  ngOnInit() {
  }

  isAdmin(){
    return this.authService.isAdmin();
  }

  isAuthenticated(){
    return this.authService.isAuthenticated();

  }

  logOut(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
