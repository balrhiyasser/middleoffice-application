import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {Router} from "@angular/router";
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  errorMessage: string;
  infoMessage: string;


  constructor(private adminService: AdminService, private router: Router) { }

  ngOnInit() {
  }

  register(){
    this.adminService.register(this.user).subscribe(data => {
      this.infoMessage = "User added successfully !";
    },err => {
      this.errorMessage = "Username is already exist !";
    });
  }

}
