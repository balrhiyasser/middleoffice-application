import {
    HttpEvent,
    HttpHandler,
    HttpRequest,
    HttpErrorResponse,
    HttpInterceptor
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class ErrorIntercept implements HttpInterceptor {

    constructor(private router: Router,private toastr: ToastrService,private authService: AuthenticationService) { }

    intercept(request: HttpRequest<any>,next: HttpHandler): Observable<HttpEvent<any>> {

        return next.handle(request)
            .pipe(
                retry(1),
                catchError((error: HttpErrorResponse) => {
                    let errorMessage = '';
                    if (error.error instanceof ErrorEvent) {
                        // client-side error
                        errorMessage = `Error: ${error.error.message}`;
                    } else {
                        // server-side error
                        if (error.message.includes("Unknown Error")){
                            this.authService.logout();
                            this.toastr.error('Oops ! Backend Server is down or temporarily unavailable.');
                            this.router.navigate(['/login']);                        
                        }
                        else {
                            errorMessage = `Error Status: ${error.status}\nMessage: ${error.message}`;
                        }
                    }
                    console.log(errorMessage);
                    return throwError(errorMessage);
                })
            )
    }


    
    
      }
