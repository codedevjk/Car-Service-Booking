import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = 'http://localhost:8080/api/bookings/dashboard';

  constructor(private http: HttpClient) { }

  // TODO: Trainee to implement API calls using HttpClient
  
  getDashboardSummary(customerId: number | null, isAdmin: boolean): Observable<any> {
    return new Observable(); // TODO: Trainee to implement
  }
}
