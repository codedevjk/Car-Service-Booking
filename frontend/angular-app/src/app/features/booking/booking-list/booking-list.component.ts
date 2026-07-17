import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { BookingService } from 'src/app/core/services/booking.service';
import { HttpClient } from '@angular/common/http';
import { catchError, forkJoin } from 'rxjs';
import { of } from 'rxjs';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.css']
})
export class BookingListComponent implements OnInit {

  @Input() embedded: boolean = false;
  bookings: any[] = [];
  isAdmin: boolean = false;
  userId: string = '';
  errorMessage: string = '';

  // Plain objects instead of Map — Angular change detection tracks object property
  // changes but does NOT track Map.set() mutations, causing "Loading..." to persist.
  vehicleMap: { [id: number]: string } = {};
  serviceMap: { [id: number]: string } = {};
  userMap:    { [id: string]: string } = {};

  constructor(
    private bookingService: BookingService,
    private http: HttpClient,
    private cdr: ChangeDetectorRef
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
    const cIds = [...new Set(this.bookings.map(b => b.customerId))] as string[];

    // Build parallel request arrays
    const vehicleRequests = vIds.map(id =>
      this.http.get<any>(`http://localhost:8080/api/vehicles/${id}`)
        .pipe(catchError(() => of(null)))
    );

    const serviceRequests = sIds.map(id =>
      this.http.get<any>(`http://localhost:8080/api/service-packages/${id}`)
        .pipe(catchError(() => of(null)))
    );

    const userRequests = this.isAdmin
      ? cIds.map(id =>
          this.http.get<any>(`http://localhost:8080/api/users/profile/${id}`)
            .pipe(catchError(() => of(null)))
        )
      : [];

    // Fire ALL requests in parallel using forkJoin — render once everything is ready
    const allRequests = [...vehicleRequests, ...serviceRequests, ...userRequests];

    if (allRequests.length === 0) return;

    forkJoin(allRequests).subscribe(results => {
      // Slice results back into their groups
      const vResults = results.slice(0, vIds.length);
      const sResults = results.slice(vIds.length, vIds.length + sIds.length);
      const uResults = results.slice(vIds.length + sIds.length);

      const newVehicleMap: { [id: number]: string } = {};
      vIds.forEach((id, i) => {
        const v = vResults[i];
        if (v) newVehicleMap[id as number] = `${v.manufacturer} ${v.model} (${v.registrationNumber})`;
      });

      const newServiceMap: { [id: number]: string } = {};
      sIds.forEach((id, i) => {
        const s = sResults[i];
        if (s) newServiceMap[id as number] = s.name;
      });

      const newUserMap: { [id: string]: string } = {};
      if (this.isAdmin) {
        cIds.forEach((id, i) => {
          const u = uResults[i];
          if (u) newUserMap[id] = `${u.fullName} (${u.email})`;
        });
      }

      // Assign new object references — triggers Angular change detection
      this.vehicleMap = newVehicleMap;
      this.serviceMap = newServiceMap;
      this.userMap    = newUserMap;
      this.cdr.markForCheck();
    });
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
