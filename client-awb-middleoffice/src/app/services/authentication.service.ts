import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';


@Injectable() 
export class AuthenticationService{

   host:string="http://localhost:8080"; 
   jwtToken:string; 
   username:string; 
   roles:Array<any>=[]; 
   
   constructor(private http:HttpClient, private router:Router){}

  login(data){
    console.log(data); 
    return this.http.post(this.host+"/login",data,{ observe: 'response' }); 
    //par défaut, il covertit la réponse en format json
    //donne toute la réponse http et non la résultat json
  } 

  /*register(user){
     return this.http.post(this.host+"/users",user);
  } */

  saveToken(jwtToken){
     this.jwtToken=jwtToken; 
     localStorage.setItem("token",jwtToken); 
     this.parseJWT();}

  parseJWT(){
    let jwtHelper=new JwtHelperService();
    let ObjectJWT=  jwtHelper.decodeToken(this.jwtToken)
    this.username=ObjectJWT?.sub;
    this.roles=ObjectJWT?.roles;

  }

  loadToken(){
    this.jwtToken=localStorage.getItem('token'); 
    this.parseJWT(); }
    
  logout(){
    localStorage.removeItem('token');
    this.initParams();
  }

  initParams(){
    this.username=undefined;
    this.jwtToken=undefined;
    this.roles=undefined;
  }
      
      
  isAdmin(){
    return this.roles?.indexOf('ADMIN')>=0;
    } 
 
   isUser(){
     return this.roles?.indexOf('USER')>=0;
   }  
   
   isAuthenticated(){
     return this.roles && (this.isAdmin() || this.isUser());
   }

  /*getTasks(){
     if(this.jwtToken==null) this.loadToken(); 
     return this.http.get(this.host+"/tasks",{headers:new HttpHeaders({'authorization':this.jwtToken})}); } 
     
  saveTask(task){ 
    let headers=new HttpHeaders(); 
    headers.append('authorization',this.jwtToken); 
    return this.http.post(this.host+"/tasks",task,{headers:new HttpHeaders({'authorization':this.jwtToken})}); } */
    
  
     
  
        
  }
