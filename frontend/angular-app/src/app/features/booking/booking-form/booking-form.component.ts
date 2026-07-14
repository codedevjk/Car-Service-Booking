import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BookingService } from 'src/app/core/services/booking.service';
import { VehicleService } from 'src/app/core/services/vehicle.service';
import { ServicePackageService } from 'src/app/core/services/service-package.service';

@Component({
  selector: 'app-booking-form',
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.css']
})
export class BookingFormComponent implements OnInit {

  bookingForm: FormGroup;
  vehicles: any[] = [];
  servicePackages: any[] = [];
  userId: string = '';
  errorMessage: string = '';
  successMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private bookingService: BookingService,
    private vehicleService: VehicleService,
    private servicePackageService: ServicePackageService
  ) {
    this.userId = localStorage.getItem('userId') || '';
    
    // Future date validator
    const today = new Date().toISOString().split('T')[0];

    this.bookingForm = this.fb.group({
      vehicleId: ['', Validators.required],
      serviceId: ['', Validators.required],
      appointmentDate: ['', [Validators.required]],
      timeSlot: ['', Validators.required],
      problemDescription: ['']
    });
  }

  ngOnInit(): void {
    this.loadVehicles();
    this.loadServices();

    // Pre-fill serviceId from query params if available
    this.route.queryParams.subscribe(params => {
      if (params['serviceId']) {
        this.bookingForm.patchValue({ serviceId: +params['serviceId'] });
      }
    });
  }

  loadVehicles(): void {
    if (!this.userId) return;
    this.vehicleService.getVehicles(this.userId).subscribe({
      next: (data) => this.vehicles = data,
      error: (err) => console.error('Error loading vehicles', err)
    });
  }

  loadServices(): void {
    this.servicePackageService.browseServicePackages(0, 100, 'name,asc').subscribe({
      next: (data) => this.servicePackages = data.content,
      error: (err) => console.error('Error loading services', err)
    });
  }

  onSubmit(): void {
    this.errorMessage = '';
    this.successMessage = '';
    
    if (this.bookingForm.invalid) {
      this.errorMessage = 'Please fill out all required fields correctly.';
      return;
    }

    const today = new Date();
    today.setHours(0,0,0,0);
    const selectedDate = new Date(this.bookingForm.value.appointmentDate);
    if (selectedDate < today) {
      this.errorMessage = 'Appointment date cannot be in the past.';
      return;
    }

    this.bookingService.createBooking(this.bookingForm.value).subscribe({
      next: (res) => {
        this.successMessage = `Booking successful! Reference Number: ${res.referenceNumber}`;
        this.bookingForm.reset();
        // Redirect after 3 seconds
        setTimeout(() => {
          this.router.navigate(['/dashboard']);
        }, 3000);
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Failed to create booking. Please try again.';
      }
    });
  }
}
