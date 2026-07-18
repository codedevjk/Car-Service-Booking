import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from 'src/app/core/services/category.service';
import { ServicePackageService } from 'src/app/core/services/service-package.service';

@Component({
  selector: 'app-service-list',
  templateUrl: './service-list.component.html',
  styleUrls: ['./service-list.component.css']
})
export class ServiceListComponent implements OnInit {

  categories: any[] = [];
  servicePackages: any[] = [];

  // Which category is currently checked (null = none selected)
  selectedCategory: any = null;

  // Inline "Add Service" form for the selected category
  addServiceForm: FormGroup;
  addServiceError = '';
  addServiceSuccess = '';

  // Package editing
  isEditingPackage = false;
  currentPackageId?: number;
  packageForm: FormGroup;

  // Keep these for any residual references (category form no longer shown)
  categoryForm: FormGroup;
  isEditingCategory = false;
  currentCategoryId?: number;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private packageService: ServicePackageService
  ) {
    this.addServiceForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      price: ['', [Validators.required, Validators.min(0)]]
    });

    // packageForm kept for edit support
    this.packageForm = this.fb.group({
      categoryId: ['', Validators.required],
      name: ['', Validators.required],
      description: [''],
      price: ['', [Validators.required, Validators.min(0)]],
      availabilityStatus: ['ACTIVE']
    });

    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      active: [true]
    });
  }

  ngOnInit(): void {
    this.loadCategories();
    this.loadPackages();
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe(res => {
      this.categories = res;
    });
  }

  loadPackages(): void {
    this.packageService.getServicePackages(0, 100).subscribe(res => {
      this.servicePackages = res.content;
    });
  }

  /** Returns the category name for a given categoryId (for display in the packages table) */
  getCategoryName(categoryId: number): string {
    const cat = this.categories.find(c => c.id === categoryId);
    return cat ? cat.name : `#${categoryId}`;
  }

  /** Toggle category selection: clicking the same checkbox again deselects */
  toggleCategorySelection(cat: any): void {
    if (this.selectedCategory?.id === cat.id) {
      this.selectedCategory = null;
      this.addServiceForm.reset();
      this.addServiceError = '';
      this.addServiceSuccess = '';
    } else {
      this.selectedCategory = cat;
      this.addServiceForm.reset();
      this.addServiceError = '';
      this.addServiceSuccess = '';
    }
  }

  /** Add a new service to the currently selected category */
  addServiceToCategory(): void {
    if (this.addServiceForm.invalid || !this.selectedCategory) return;
    this.addServiceError = '';
    this.addServiceSuccess = '';

    const payload = {
      categoryId: this.selectedCategory.id,
      name: this.addServiceForm.value.name,
      description: this.addServiceForm.value.description,
      price: this.addServiceForm.value.price,
      availabilityStatus: 'ACTIVE'
    };

    this.packageService.addServicePackage(payload).subscribe({
      next: () => {
        this.addServiceSuccess = `Service added to "${this.selectedCategory.name}" successfully!`;
        this.addServiceForm.reset();
        this.loadPackages();
        // Auto-hide the panel (deselect category) after 5 seconds
        setTimeout(() => {
          this.selectedCategory = null;
          this.addServiceSuccess = '';
        }, 5000);
      },
      error: (err: any) => {
        this.addServiceError = err.error?.message || 'Failed to add service. Please try again.';
      }
    });
  }

  // --- Package edit / delete ---
  editPackage(pkg: any): void {
    this.isEditingPackage = true;
    this.currentPackageId = pkg.id;
    this.packageForm.patchValue(pkg);
  }

  savePackage(): void {
    if (this.packageForm.invalid) return;
    const val = this.packageForm.value;
    if (this.isEditingPackage && this.currentPackageId) {
      this.packageService.updateServicePackage(this.currentPackageId, val).subscribe(() => {
        this.resetPackageForm();
        this.loadPackages();
      });
    }
  }

  deletePackage(id: number): void {
    this.packageService.deleteServicePackage(id).subscribe(() => this.loadPackages());
  }

  resetPackageForm(): void {
    this.isEditingPackage = false;
    this.currentPackageId = undefined;
    this.packageForm.reset({ availabilityStatus: 'ACTIVE', categoryId: '' });
  }

  // Category actions kept for future use
  saveCategory(): void { }
  editCategory(cat: any): void { }
  deleteCategory(id: number): void { }
  resetCategoryForm(): void {
    this.isEditingCategory = false;
    this.currentCategoryId = undefined;
    this.categoryForm.reset({ active: true });
  }
}
