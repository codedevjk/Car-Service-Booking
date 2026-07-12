import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarServiceService {
  private apiUrl = 'http://localhost:8080/api/services';

  constructor(private http: HttpClient) { }

  // TODO: Trainee to implement US05 (Car Service Management)
  // 1. Implement getServices(): Observable<any[]>
  // 2. Implement addService(service: any): Observable<any>
  // 3. Implement updateService(id: number, service: any): Observable<any>
  // 4. Implement deleteService(id: number): Observable<any>

  // TODO: Trainee to implement US06 (Browse & Search Services)
  // 1. Implement searchServices(name: string): Observable<any[]>
  // 2. Implement filterByCategory(categoryId: number): Observable<any[]>
}
