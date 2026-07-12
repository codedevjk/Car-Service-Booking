import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },
  { path: 'auth', loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule) },
  { path: 'dashboard', loadChildren: () => import('./features/dashboard/dashboard.module').then(m => m.DashboardModule) },
  { path: 'profile', loadChildren: () => import('./features/profile/profile.module').then(m => m.ProfileModule) },
  { path: 'vehicles', loadChildren: () => import('./features/vehicle/vehicle.module').then(m => m.VehicleModule) },
  { path: 'categories', loadChildren: () => import('./features/category/category.module').then(m => m.CategoryModule) },
  { path: 'services', loadChildren: () => import('./features/service/service.module').then(m => m.ServiceModule) },
  { path: 'bookings', loadChildren: () => import('./features/booking/booking.module').then(m => m.BookingModule) },
  { path: 'reports', loadChildren: () => import('./features/reports/reports.module').then(m => m.ReportsModule) },
  { path: '**', redirectTo: 'auth/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
