import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.css']
})
export class BookingListComponent implements OnInit {

  // TODO: Trainee to implement US08 (Booking Management)
  // 1. Inject BookingService.
  // 2. Fetch the current logged-in customer's bookings.
  // 3. Store bookings in a local array and render in the HTML table.
  // 4. Implement a method to allow customers to cancel a booking (change status to CANCELLED).

  constructor() { }

  ngOnInit(): void {
    // TODO: Load bookings on initialization
  }
}
