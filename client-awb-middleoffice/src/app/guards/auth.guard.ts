import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import {UserService} from '../services/user.service';
import {User} from "../model/user";
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements  CanActivate {
  constructor(private router: Router, private authService: AuthenticationService){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    if(this.authService.isAuthenticated()){
      //check if route is restricted by role...
      console.log(this.authService.roles);
      console.log(route.data.roles);
      if(route.data.roles.includes(this.authService.roles)){
        this.router.navigate(['/401']);
        return false;
      }
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}
