import { Component, OnInit } from '@angular/core';
import { ServicePackageService } from 'src/app/core/services/service-package.service';
import { CategoryService } from 'src/app/core/services/category.service';

@Component({
  selector: 'app-service-browse',
  templateUrl: './service-browse.component.html',
  styleUrls: ['./service-browse.component.css']
})
export class ServiceBrowseComponent implements OnInit {
  
  categories: any[] = [];
  servicePackages: any[] = [];
  
  selectedCategoryId?: number;
  searchQuery: string = '';
  sortBy: string = 'name,asc';
  
  page = 0;
  size = 6;
  totalElements = 0;
  totalPages = 0;

  constructor(
    private servicePackageService: ServicePackageService,
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.loadCategories();
    this.loadServicePackages();
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: (data) => this.categories = data,
      error: (err) => console.error('Error loading categories', err)
    });
  }

  loadServicePackages(): void {
    this.servicePackageService.browseServicePackages(this.page, this.size, this.sortBy, this.searchQuery, this.selectedCategoryId).subscribe({
      next: (data) => {
        this.servicePackages = data.content;
        this.totalElements = data.totalElements;
        this.totalPages = data.totalPages;
      },
      error: (err) => console.error('Error loading service packages', err)
    });
  }

  selectCategory(categoryId?: number): void {
    this.selectedCategoryId = categoryId;
    this.page = 0;
    this.loadServicePackages();
  }

  onSearchChange(): void {
    this.page = 0;
    this.loadServicePackages();
  }

  onSortChange(event: any): void {
    this.sortBy = event.target.value;
    this.page = 0;
    this.loadServicePackages();
  }

  changePage(newPage: number): void {
    if (newPage >= 0 && newPage < this.totalPages) {
      this.page = newPage;
      this.loadServicePackages();
    }
  }
}
