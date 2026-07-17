import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },
  { path: 'auth', loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule) },
  {
    path: 'dashboard',
    loadChildren: () => import('./features/dashboard/dashboard.module').then(m => m.DashboardModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'profile',
    loadChildren: () => import('./features/profile/profile.module').then(m => m.ProfileModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'vehicles',
    loadChildren: () => import('./features/vehicle/vehicle.module').then(m => m.VehicleModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'categories',
    loadChildren: () => import('./features/category/category.module').then(m => m.CategoryModule),
    canActivate: [AuthGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'services',
    loadChildren: () => import('./features/service/service.module').then(m => m.ServiceModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'bookings',
    loadChildren: () => import('./features/booking/booking.module').then(m => m.BookingModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'reports',
    loadChildren: () => import('./features/reports/reports.module').then(m => m.ReportsModule),
    canActivate: [AuthGuard]
  },
  { path: '**', redirectTo: 'auth/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }