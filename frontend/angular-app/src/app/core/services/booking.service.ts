import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = 'http://localhost:8080/api/bookings';

  constructor(private http: HttpClient) { }

  // TODO: Trainee to implement API calls using HttpClient
  
  createBooking(booking: any): Observable<any> {
    return new Observable(); // TODO: Trainee to implement
  }

  updateBookingStatus(id: number, status: string): Observable<any> {
    return new Observable(); // TODO: Trainee to implement
  }

  getCustomerBookings(customerId: number): Observable<any[]> {
    return new Observable(); // TODO: Trainee to implement
  }

  searchBookings(referenceNumber: string): Observable<any[]> {
    return new Observable(); // TODO: Trainee to implement
  }
}
