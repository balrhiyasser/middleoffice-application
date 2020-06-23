import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../services/user.service";
import {User} from "../../../model/user";
import {Router} from "@angular/router";
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  username: String;
  role:any;

  constructor(private authService: AuthenticationService, private router: Router) {
    this.username=this.authService.username;
    this.role=this.authService.roles;
   }

  ngOnInit() {
  }

  logOut(){
    this.authService.logout();
    this.router.navigate(['/login']);

  }

}
