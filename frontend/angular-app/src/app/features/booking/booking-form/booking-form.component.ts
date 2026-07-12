import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-booking-form',
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.css']
})
export class BookingFormComponent implements OnInit {

  // TODO: Trainee to implement US07 (Car Service Booking)
  // 1. Inject FormBuilder, BookingService, VehicleService, and CarServiceService.
  // 2. Initialize bookingForm (Reactive Form) with fields: vehicleId, serviceId, date, time, notes.
  // 3. Load the customer's vehicles to populate the vehicle dropdown.
  // 4. Load all available car services to populate the service dropdown.
  // 5. Implement onSubmit() to create the booking via BookingService.

  constructor() { }

  ngOnInit(): void {
    // TODO: Initialize form and dropdown data
  }
}
