import { Component, OnInit } from '@angular/core';
import { BookingService } from 'src/app/core/services/booking.service';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.css']
})
export class BookingListComponent implements OnInit {

  bookings: any[] = [];
  isAdmin: boolean = false;
  userId: string = '';
  errorMessage: string = '';

  vehicleMap: Map<number, string> = new Map();
  serviceMap: Map<number, string> = new Map();
  userMap: Map<string, string> = new Map();

  constructor(
    private bookingService: BookingService,
    private http: HttpClient
  ) {
    this.userId = localStorage.getItem('userId') || '';
    this.isAdmin = this.userId.startsWith('A');
  }

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings(): void {
    if (this.isAdmin) {
      this.bookingService.getAllBookings(0, 100).subscribe({
        next: (data) => {
          this.bookings = data.content;
          this.hydrateData();
        },
        error: (err) => this.errorMessage = 'Failed to load bookings'
      });
    } else {
      this.bookingService.getCustomerBookings(this.userId).subscribe({
        next: (data) => {
          this.bookings = data;
          this.hydrateData();
        },
        error: (err) => this.errorMessage = 'Failed to load bookings'
      });
    }
  }

  hydrateData(): void {
    const vIds = [...new Set(this.bookings.map(b => b.vehicleId))];
    const sIds = [...new Set(this.bookings.map(b => b.serviceId))];
    const cIds = [...new Set(this.bookings.map(b => b.customerId))];

    vIds.forEach(id => {
      if (!this.vehicleMap.has(id)) {
        this.http.get<any>(`http://localhost:8080/api/vehicles/${id}`)
          .pipe(catchError(() => of(null)))
          .subscribe(v => {
            if (v) this.vehicleMap.set(id, `${v.manufacturer} ${v.model} (${v.registrationNumber})`);
          });
      }
    });

    sIds.forEach(id => {
      if (!this.serviceMap.has(id)) {
        this.http.get<any>(`http://localhost:8080/api/service-packages/${id}`)
          .pipe(catchError(() => of(null)))
          .subscribe(s => {
            if (s) this.serviceMap.set(id, s.name);
          });
      }
    });

    if (this.isAdmin) {
      cIds.forEach(id => {
        if (!this.userMap.has(id)) {
          this.http.get<any>(`http://localhost:8080/api/users/profile/${id}`)
            .pipe(catchError(() => of(null)))
            .subscribe(u => {
              if (u) this.userMap.set(id, `${u.firstName} ${u.lastName} (${u.email})`);
            });
        }
      });
    }
  }

  updateStatus(booking: any, event: Event): void {
    const newStatus = (event.target as HTMLSelectElement).value;
    this.bookingService.updateBookingStatus(booking.id, newStatus).subscribe({
      next: (updated) => {
        booking.status = updated.status;
        this.errorMessage = '';
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Invalid status transition';
        (event.target as HTMLSelectElement).value = booking.status; // revert
      }
    });
  }

  getValidTransitions(currentStatus: string): string[] {
    switch (currentStatus) {
      case 'PENDING': return ['PENDING', 'CONFIRMED', 'CANCELLED'];
      case 'CONFIRMED': return ['CONFIRMED', 'IN_SERVICE'];
      case 'IN_SERVICE': return ['IN_SERVICE', 'READY_FOR_DELIVERY'];
      case 'READY_FOR_DELIVERY': return ['READY_FOR_DELIVERY', 'COMPLETED'];
      case 'COMPLETED': return ['COMPLETED'];
      case 'CANCELLED': return ['CANCELLED'];
      default: return [currentStatus];
    }
  }
}
