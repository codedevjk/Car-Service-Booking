import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = 'http://localhost:8080/api/bookings';

  constructor(private http: HttpClient) { }
  
  createBooking(booking: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, booking);
  }

  cancelBooking(id: number): Observable<any> {
    return this.http.patch<any>(`${this.apiUrl}/${id}/cancel`, {});
  }

  updateBookingStatus(id: number, status: string): Observable<any> {
    const params = { status };
    return this.http.patch<any>(`${this.apiUrl}/${id}/status`, null, { params });
  }

  getCustomerBookings(customerId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/customer/${customerId}`);
  }

  getAllBookings(page: number = 0, size: number = 10, sort: string = 'id,desc'): Observable<any> {
    const params = { page: page.toString(), size: size.toString(), sort };
    return this.http.get<any>(this.apiUrl, { params });
  }

  searchBookings(referenceNumber?: string, customerName?: string, status?: string, appointmentDate?: string, page: number = 0, size: number = 10, sort: string = 'id,desc'): Observable<any> {
    let params: any = { page: page.toString(), size: size.toString(), sort };
    if (referenceNumber) params.referenceNumber = referenceNumber;
    if (customerName) params.customerName = customerName;
    if (status) params.status = status;
    if (appointmentDate) params.appointmentDate = appointmentDate;
    
    return this.http.get<any>(`${this.apiUrl}/search`, { params });
  }

  getBookingStatistics(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/statistics`);
  }

  getDashboardSummary(customerId: string, isAdmin: boolean): Observable<any> {
    let params: any = { isAdmin: isAdmin };
    if (!isAdmin) {
      params.customerId = customerId;
    }
    return this.http.get<any>(`${this.apiUrl}/dashboard`, { params });
  }
}
