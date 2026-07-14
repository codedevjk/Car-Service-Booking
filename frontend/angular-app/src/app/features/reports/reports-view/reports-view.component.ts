import { Component, OnInit } from '@angular/core';
import { BookingService } from 'src/app/core/services/booking.service';

@Component({
  selector: 'app-reports-view',
  templateUrl: './reports-view.component.html',
  styleUrls: ['./reports-view.component.css']
})
export class ReportsViewComponent implements OnInit {

  isAdmin: boolean = false;
  userId: string = '';
  
  // Search Filters
  searchRef: string = '';
  searchName: string = '';
  searchStatus: string = '';
  searchDate: string = '';

  // Results
  bookings: any[] = [];
  statistics: any = {};
  errorMessage: string = '';

  // Pagination
  page: number = 0;
  size: number = 10;
  totalPages: number = 0;

  constructor(private bookingService: BookingService) {
    this.userId = localStorage.getItem('userId') || '';
    this.isAdmin = this.userId.startsWith('A');
  }

  ngOnInit(): void {
    this.loadStatistics();
    this.search();
  }

  loadStatistics(): void {
    this.bookingService.getBookingStatistics().subscribe({
      next: (data) => this.statistics = data,
      error: () => this.errorMessage = 'Failed to load statistics'
    });
  }

  search(): void {
    this.bookingService.searchBookings(this.searchRef, this.searchName, this.searchStatus, this.searchDate, this.page, this.size).subscribe({
      next: (data) => {
        this.bookings = data.content || [];
        this.totalPages = data.totalPages || 0;
        if (this.bookings.length === 0) {
          this.errorMessage = 'No matching records found.';
        } else {
          this.errorMessage = '';
        }
      },
      error: () => this.errorMessage = 'Failed to load search results'
    });
  }

  clear(): void {
    this.searchRef = '';
    this.searchName = '';
    this.searchStatus = '';
    this.searchDate = '';
    this.page = 0;
    this.search();
  }

  nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.search();
    }
  }

  prevPage(): void {
    if (this.page > 0) {
      this.page--;
      this.search();
    }
  }

  objectKeys(obj: any): string[] {
    return Object.keys(obj || {});
  }
}
