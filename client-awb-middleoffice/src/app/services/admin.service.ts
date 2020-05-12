import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';
import {Product} from '../model/product';
import { CoursBBE } from '../model/coursBBE';
import { Parameter } from '../model/parameter';

let API_URL = "http://localhost:8080/api/admin/";

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  currentUser: User;
  headers: HttpHeaders;

  constructor(private http: HttpClient) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.headers = new HttpHeaders({
      authorization:'Bearer '+ this.currentUser.token,
      "Content-Type":"application/json; charset=UTF-8"
    });
  }

  register(user: User): Observable<any> {
    return this.http.post(API_URL + "registration", JSON.stringify(user),
  {headers: this.headers});
  }

  getcoursbbe(date): Observable<any> {
    const params = new HttpParams().set('date', date);
    return this.http.get("http://localhost:8080/coursbbe", {params,
  headers: this.headers});
  }

  getcourbebdt(date): Observable<any> {
    const params = new HttpParams().set('dateCourbe', date);
    return this.http.get("http://localhost:8080/courbebdt", {params,
  headers: this.headers});
  }

  updateUser(user: User): Observable<any> {
    return this.http.put(API_URL + "user-update", JSON.stringify(user),
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

  //products
  createProduct(product: Product): Observable<any> {
    return this.http.post(API_URL + "product-create", JSON.stringify(product),
  {headers: this.headers});
  }

  updateProduct(product: Product): Observable<any> {
    return this.http.put(API_URL + "product-update", JSON.stringify(product),
  {headers: this.headers});
  }

  deleteProduct(product: Product): Observable<any> {
    return this.http.post(API_URL + "product-delete", JSON.stringify(product),
  {headers: this.headers});
  }

  findAllProducts(): Observable<any> {
    return this.http.get(API_URL + "product-all",
  {headers: this.headers});
  }

  numberOfProducts(): Observable<any> {
    return this.http.get(API_URL + "product-number",
  {headers: this.headers});
  }

  //transactions
  findAllTransactions(): Observable<any> {
    return this.http.get(API_URL + "transaction-all",
   {headers: this.headers});
  }

  numberOfTransactions(): Observable<any> {
    return this.http.get(API_URL + "transaction-number",
  {headers: this.headers});
  }
}
