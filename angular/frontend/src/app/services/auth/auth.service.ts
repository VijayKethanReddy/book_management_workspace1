import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8082/userauth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  register(name: string, userName: string, emailId: string, password: string, role: any): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      name,
      userName,
      emailId,
      password,
      role
    }, httpOptions);
  }

  login(userName: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      userName,
      password
    }, httpOptions);
  }
}
