import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl = 'http://localhost:8080/api/users/profile';

  constructor(private http: HttpClient) { }

  getProfile(email: string): Observable<any> {
    return this.http.get(`${this.apiUrl}?email=${email}`);
  }

  updateProfile(email: string, profile: any): Observable<any> {
    return this.http.put(`${this.apiUrl}?email=${email}`, profile);
  }
}
