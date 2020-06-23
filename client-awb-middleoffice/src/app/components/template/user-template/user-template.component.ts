import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../services/user.service";
import {User} from "../../../model/user";
import {Role} from "../../../model/role";
import {Router} from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-user-template',
  templateUrl: './user-template.component.html',
  styleUrls: ['./user-template.component.css']
})
export class UserTemplateComponent implements OnInit {
  username: string;
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
