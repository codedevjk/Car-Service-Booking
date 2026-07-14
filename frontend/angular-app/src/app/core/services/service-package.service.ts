import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServicePackageService {
  private apiUrl = 'http://localhost:8080/api/service-packages';

  constructor(private http: HttpClient) { }

  getServicePackages(page: number, size: number, categoryId?: number): Observable<any> {
    let params: any = { page: page.toString(), size: size.toString() };
    if (categoryId) {
      params.categoryId = categoryId.toString();
    }
    return this.http.get<any>(this.apiUrl, { params });
  }

  browseServicePackages(page: number, size: number, sort: string, search?: string, categoryId?: number): Observable<any> {
    let params: any = { page: page.toString(), size: size.toString(), sort: sort };
    if (search && search.trim() !== '') {
      params.search = search.trim();
    }
    if (categoryId) {
      params.categoryId = categoryId.toString();
    }
    return this.http.get<any>(`${this.apiUrl}/browse`, { params });
  }

  getServicePackageById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  addServicePackage(servicePackage: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, servicePackage);
  }

  updateServicePackage(id: number, servicePackage: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, servicePackage);
  }

  deleteServicePackage(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
