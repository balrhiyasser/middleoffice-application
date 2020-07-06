import { Component, OnInit } from '@angular/core';
import {AdminService} from '../../../services/admin.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { CoursBBE } from 'src/app/model/coursBBE';
import { CourbeBDT } from 'src/app/model/courbeBDT';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  userCount:any = "";
  today: number = Date.now();
  time : string = new Date().toTimeString();
  todayString : string = new Date().toLocaleDateString();
  todayFormattedString : string =this.todayString.substring(6,10)+"-"+this.todayString.substring(3,5)+"-"+this.todayString.substring(0,2);
  recupbdtmessage: string
  generatebdtmessage: string
  recupbbemessage: string
  generatebbemessage: string

  constructor(private adminService: AdminService, private authService: AuthenticationService) { }

  ngOnInit() {
    if(this.isAdmin())
    this.findcoursbbe(this.todayFormattedString);
    this.findcourbebdt(this.todayFormattedString);
  }

  isAdmin(){
    return this.authService.isAdmin();
  }

  findcoursbbe(todayFormattedString){
    this.adminService.findcoursbbe(todayFormattedString).subscribe(data => {
      if(data.length==0){
          this.recupbbemessage="Non récupérée"
          this.generatebbemessage="Non générée"
      }else{
        if(data[0].midBAM==null){
          this.recupbbemessage="Bien récupérée"
          this.generatebbemessage="Non générée"
        }else{
          this.recupbbemessage="Bien récupérée"
          this.generatebbemessage="Bien générée"
        }
      }
    });
  }

  findcourbebdt(todayFormattedString){
    this.adminService.findcourbebdt(todayFormattedString).subscribe(data => {
      if(data.length==0){
        this.recupbdtmessage="Non récupérée"
        this.generatebdtmessage="Non générée"
      }else{
        if(data[0].maturite==null){
          this.recupbdtmessage="Bien récupérée"
          this.generatebdtmessage="Non générée"
        }else{
          this.recupbdtmessage="Bien récupérée"
          this.generatebdtmessage="Bien générée"
        }
      }
    });
  }

  numberOfUsers(){
    this.adminService.numberOfUsers().subscribe(data => {
      this.userCount = data.response;
    });
  }

}
