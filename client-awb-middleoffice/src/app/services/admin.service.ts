import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';
import { Parameter } from '../model/parameter';
import { AuthenticationService } from './authentication.service';

let API_URL = "http://localhost:8080/api/admin/";

@Injectable({
  providedIn: 'root'
})
export class AdminService {
 
  headers: HttpHeaders;

  constructor(private http: HttpClient, private authService: AuthenticationService) {
    this.headers = new HttpHeaders({
      authorization:'Bearer '+ this.authService.jwtToken,"Content-Type":"application/json; charset=UTF-8"
    });
  }

  register(user: User): Observable<any> {
    return this.http.post(API_URL + "registration", JSON.stringify(user),
  {headers: this.headers});
  }

  getcoursbbe(date): Observable<any> {
    const params = new HttpParams().set('dateCourbe', date);
    return this.http.get("http://localhost:8080/coursbbe", {params,
  headers: this.headers});
  }

  getcourbebdt(date): Observable<any> {
    const params = new HttpParams().set('dateCourbe', date);
    return this.http.get("http://localhost:8080/courbebdt", {params,
  headers: this.headers});
  }

  //////////////// traitement de calcul //////////////////////

  generateBBE(date): Observable<any> {
    const params = new HttpParams().set('dateCourbe', date);
    return this.http.get("http://localhost:8080/coursbbe/generate", {params,
  headers: this.headers});

  }
  
  generateBDT(date): Observable<any> {
    const params = new HttpParams().set('dateCourbe', date);
    return this.http.get("http://localhost:8080/courbebdt/generate", {params,
  headers: this.headers});

  }

  /////////////////////////////////////////////////////////////

  generateCourbeST(date): Observable<any> {

    const params = new HttpParams().set('dateCourbe', date);
    return this.http.get("http://localhost:8080/courbebdt/shorterm", {params,
  headers: this.headers});

  }

  generateCourbeLT(date): Observable<any> {

    const params = new HttpParams().set('dateCourbe', date);
    return this.http.get("http://localhost:8080/courbebdt/longterm", {params,
  headers: this.headers});

  }

  generateTaux(date): Observable<any> {

    const params = new HttpParams().set('dateTaux', date);
    return this.http.get("http://localhost:8080/tmpjj", {params,
  headers: this.headers});

  }

  /////////////////////////////////////////////////////////////

  updateUser(user: User): Observable<any> {
    return this.http.put("http://localhost:8080/api/user-update", JSON.stringify(user),
  {headers: this.headers});
  }

  deleteUser(user: User): Observable<any> {
    return this.http.post(API_URL + "user-delete", JSON.stringify(user),
  {headers: this.headers});
  }

  findAllUsers(): Observable<any> {
    return this.http.get(API_URL + "user-all",
  {headers: this.headers});
  }

  findUserDetails(username): Observable<any> {
    const params = new HttpParams().set('username', username);
    return this.http.get("http://localhost:8080/api/user-details",{params,
    headers: this.headers});
  }


  numberOfUsers(): Observable<any> {
    return this.http.get(API_URL + "user-number",
  {headers: this.headers});
  }

  //settings
  createParameter(parameter: Parameter): Observable<any> {
    return this.http.post(API_URL + "settings-create", JSON.stringify(parameter),
  {headers: this.headers});
  }

  updateParameter(parameter: Parameter): Observable<any> {
    return this.http.put(API_URL + "settings-update", JSON.stringify(parameter),
  {headers: this.headers});
  }

  deleteParameter(parameter: Parameter): Observable<any> {
    return this.http.post(API_URL + "settings-delete", JSON.stringify(parameter),
  {headers: this.headers});
  }

  findAllParameters(): Observable<any> {
    return this.http.get(API_URL + "settings-all",
  {headers: this.headers});
  }

  numberOfParameters(): Observable<any> {
    return this.http.get(API_URL + "settings-number",
  {headers: this.headers});
  }

  //download files
  /*public async DownloadSTFile(date): Promise<Blob> {
    const params = new HttpParams().set('date', date);
    const file =  await this.http.get<Blob>(
      "http://localhost:8080/download/COURBE_MS_ST_"+date.substring(8,10)+date.substring(5,7)+date.substring(0,4)+".csv",{params,headers: this.headers,responseType: ResponseContentType.Blob}).toPromise();
    return file;
  }*/

  DownloadSTFile(date): Observable<any>{
    const params = new HttpParams().set('date', date);
		return this.http.get('http://localhost:8080/download/shorterm', {params,headers: this.headers, responseType: 'blob'});
  }

  DownloadLTFile(date): Observable<any>{
    const params = new HttpParams().set('date', date);
		return this.http.get('http://localhost:8080/download/longterm', {params,headers: this.headers, responseType: 'blob'});
  }

  DownloadTMPFile(date): Observable<any>{
    const params = new HttpParams().set('date', date);
		return this.http.get('http://localhost:8080/download/tmp', {params,headers: this.headers, responseType: 'blob'});
  }

  DownloadCSVFile(date): Observable<any>{
    const params = new HttpParams().set('date', date);
		return this.http.get('http://localhost:8080/download/coursbbecsv', {params,headers: this.headers, responseType: 'blob'});
  }

  DownloadEXFile(date): Observable<any>{
    const params = new HttpParams().set('date', date);
		return this.http.get('http://localhost:8080/download/coursbbeexcel', {params,headers: this.headers, responseType: 'blob'});
  }

  DownloadBAMFile(date): Observable<any>{
    const params = new HttpParams().set('date', date);
		return this.http.get('http://localhost:8080/download/BAMFX03', {params,headers: this.headers, responseType: 'blob'});
  }

  DownloadWAFAFile(date): Observable<any>{
    const params = new HttpParams().set('date', date);
		return this.http.get('http://localhost:8080/download/WAFACASH', {params,headers: this.headers, responseType: 'blob'});
  }

}
