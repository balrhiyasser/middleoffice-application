import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-default-template',
  templateUrl: './default-template.component.html',
  styleUrls: ['./default-template.component.css']
})
export class DefaultTemplateComponent implements OnInit {

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
