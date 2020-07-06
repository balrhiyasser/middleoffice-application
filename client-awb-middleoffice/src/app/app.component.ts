import { Component, OnInit} from '@angular/core';
import { AuthenticationService } from './services/authentication.service';
import { ConnectionService } from 'ng-connection-service';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from "ngx-spinner";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title = 'client-awb-middleoffice';

  status = 'ONLINE';
  isConnected = true;

  constructor(private authService: AuthenticationService,private spinner: NgxSpinnerService, private toastr: ToastrService, private connectionService: ConnectionService){
    this.connectionService.monitor().subscribe(isConnected => {
      this.isConnected = isConnected;
      if (this.isConnected) {
        this.spinner.hide("deconnected");
        this.spinner.show("connected");
        setTimeout(() => {
          this.spinner.hide("connected");
        }, 2000);
      }
      else {
        this.spinner.show("deconnected");
      }
    })
  }


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
