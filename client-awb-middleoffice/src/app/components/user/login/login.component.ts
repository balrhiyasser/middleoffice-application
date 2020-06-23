import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../services/user.service';
import {User} from '../../../model/user';
import {Router} from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();
  errorMessage:string;

  constructor(private authService: AuthenticationService, private router: Router) { }

  ngOnInit() {
    
  }

  onLogin(data){ 
    this.authService.login(data).subscribe(resp=> { 
      let jwtToken=resp.headers.get('Authorization');
      console.log(data); 
      console.log(jwtToken);
      this.authService.saveToken(jwtToken); 
      this.router.navigate(['/dashboard']); 
    }, err=>{ 
      this.errorMessage = "Username or password is incorrect.";
    })
}

  

  /*login(){
    this.userService.login(this.user).subscribe(data => {
      this.router.navigate(['/profile']);
    },err => {
      this.errorMessage = "Username or password is incorrect.";
    });
  }*/

  
  onRegister(){ 
    this.router.navigateByUrl("/register"); 
  }


}
