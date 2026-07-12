import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-view',
  templateUrl: './dashboard-view.component.html',
  styleUrls: ['./dashboard-view.component.css']
})
export class DashboardViewComponent implements OnInit {

  // TODO: Trainee to implement US09 (Dashboard & Booking History)
  // 1. Inject DashboardService and Auth service to get current user role.
  // 2. Fetch the dashboard summary (Admin sees all stats, User sees only their stats).
  // 3. Store stats (total bookings, revenue, pending tasks) in local variables.
  // 4. (Optional) Initialize chart logic.

  constructor() { }

  ngOnInit(): void {
    // TODO: Load dashboard statistics based on role
  }
}
