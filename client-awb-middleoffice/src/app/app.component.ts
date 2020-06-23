import { Component, OnInit} from '@angular/core';
import {UserService} from './services/user.service';
import {Router} from '@angular/router';
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title = 'client-awb-middleoffice';

  constructor(private authService: AuthenticationService, private router: Router){};
  

  ngOnInit(): void {
    this.authService.loadToken();  
  }


  isAdmin(){
    return this.authService.isAdmin();
  }

  isAuthenticated(){
    return this.authService.isAuthenticated();

  }
  
}
