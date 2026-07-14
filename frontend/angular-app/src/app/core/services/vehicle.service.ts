import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {
  private apiUrl = 'http://localhost:8080/api/vehicles';

  constructor(private http: HttpClient) { }

  getVehicles(userId: string): Observable<any[]> {
    const headers = { 'X-User-Id': userId };
    return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`, { headers });
  }

  getVehicle(id: number, userId: string): Observable<any> {
    const headers = { 'X-User-Id': userId };
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers });
  }

  addVehicle(vehicle: any, userId: string): Observable<any> {
    const headers = { 'X-User-Id': userId };
    return this.http.post<any>(this.apiUrl, vehicle, { headers });
  }

  updateVehicle(id: number, vehicle: any, userId: string): Observable<any> {
    const headers = { 'X-User-Id': userId };
    return this.http.put<any>(`${this.apiUrl}/${id}`, vehicle, { headers });
  }

  deleteVehicle(id: number, userId: string): Observable<any> {
    const headers = { 'X-User-Id': userId };
    return this.http.delete<any>(`${this.apiUrl}/${id}`, { headers });
  }
}
