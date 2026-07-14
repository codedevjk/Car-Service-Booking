import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl = 'http://localhost:8080/api/users/profile';

  constructor(private http: HttpClient) { }

  getProfile(userId: string): Observable<any> {
    const headers = { 'X-User-Id': userId };
    return this.http.get(`${this.apiUrl}/${userId}`, { headers });
  }

  updateProfile(userId: string, profile: any): Observable<any> {
    const headers = { 'X-User-Id': userId };
    return this.http.put(`${this.apiUrl}/${userId}`, profile, { headers });
  }
}
