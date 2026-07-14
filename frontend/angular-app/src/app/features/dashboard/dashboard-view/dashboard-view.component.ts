import { Component, OnInit } from '@angular/core';
import { BookingService } from 'src/app/core/services/booking.service';

@Component({
  selector: 'app-dashboard-view',
  templateUrl: './dashboard-view.component.html',
  styleUrls: ['./dashboard-view.component.css']
})
export class DashboardViewComponent implements OnInit {

  isAdmin: boolean = false;
  userId: string = '';
  summary: any = {};
  errorMessage: string = '';

  constructor(private bookingService: BookingService) {
    this.userId = localStorage.getItem('userId') || '';
    this.isAdmin = this.userId.startsWith('A');
  }

  ngOnInit(): void {
    this.bookingService.getDashboardSummary(this.userId, this.isAdmin).subscribe({
      next: (data) => this.summary = data,
      error: (err) => this.errorMessage = 'Failed to load dashboard statistics'
    });
  }
}
