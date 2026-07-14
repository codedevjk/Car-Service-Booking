import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (request.url.includes('/api/')) {
      const userId = localStorage.getItem('userId');
      const userRole = localStorage.getItem('role') || 'CUSTOMER';

      let headers = request.headers;
      
      if (userId) {
        headers = headers.set('X-User-Id', userId);
        headers = headers.set('X-User-Role', userRole);
      }

      const authReq = request.clone({ headers });
      return next.handle(authReq);
    }

    return next.handle(request);
  }
}
