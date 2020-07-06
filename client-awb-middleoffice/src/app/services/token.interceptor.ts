import {
    HttpEvent,
    HttpHandler,
    HttpRequest,
    HttpErrorResponse,
    HttpInterceptor
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class TokenIntercept implements HttpInterceptor {

    constructor(private router: Router,private toastr: ToastrService, private authService: AuthenticationService) { }

    intercept(request: HttpRequest<any>,next: HttpHandler): Observable<HttpEvent<any>> {

        var jwtToken=localStorage.getItem('token')
        console.log(jwtToken);
        let jwtHelper=new JwtHelperService();
        if (jwtToken != null && jwtHelper.isTokenExpired(jwtToken)) {
          this.authService.logout();
          this.toastr.error('Veuillez vous connecter','Session expirée ! ');
          this.router.navigate(['/login']);
          return throwError("Session expirée !");
        } else {
    
          const authRequest = request.clone({
            setHeaders: {
              Authorization: 'Bearer ' + jwtToken
            }
          })
          return next.handle(authRequest)
            .pipe(
              tap(event => {
              }, error => {
              })
            )
        }
    }

}