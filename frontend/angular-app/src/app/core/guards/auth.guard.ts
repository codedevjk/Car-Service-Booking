import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree {
    if (!this.authService.isLoggedIn()) {
      return this.router.createUrlTree(['/auth/login']);
    }

    const allowedRoles: string[] = route.data['roles'];
    if (allowedRoles && allowedRoles.length > 0) {
      const userRole = this.authService.getRole();
      if (!userRole || !allowedRoles.includes(userRole)) {
        return this.router.createUrlTree(['/dashboard']);
      }
    }

    return true;
  }
}