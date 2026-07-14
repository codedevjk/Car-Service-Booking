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
  
  categoryForm: FormGroup;
  packageForm: FormGroup;

  isEditingCategory = false;
  isEditingPackage = false;
  currentCategoryId?: number;
  currentPackageId?: number;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private packageService: ServicePackageService
  ) {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      active: [true]
    });

    this.packageForm = this.fb.group({
      categoryId: ['', Validators.required],
      name: ['', Validators.required],
      description: [''],
      price: ['', [Validators.required, Validators.min(0)]],
      availabilityStatus: ['ACTIVE']
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

  // --- Category Actions ---
  saveCategory(): void {
    if (this.categoryForm.invalid) return;
    const val = this.categoryForm.value;

    if (this.isEditingCategory && this.currentCategoryId) {
      this.categoryService.updateCategory(this.currentCategoryId, val).subscribe(() => {
        this.resetCategoryForm();
        this.loadCategories();
      });
    } else {
      this.categoryService.addCategory(val).subscribe(() => {
        this.resetCategoryForm();
        this.loadCategories();
      });
    }
  }

  editCategory(cat: any): void {
    this.isEditingCategory = true;
    this.currentCategoryId = cat.id;
    this.categoryForm.patchValue(cat);
  }

  deleteCategory(id: number): void {
    this.categoryService.deleteCategory(id).subscribe(() => this.loadCategories());
  }

  resetCategoryForm(): void {
    this.isEditingCategory = false;
    this.currentCategoryId = undefined;
    this.categoryForm.reset({ active: true });
  }

  // --- Package Actions ---
  savePackage(): void {
    if (this.packageForm.invalid) return;
    const val = this.packageForm.value;

    if (this.isEditingPackage && this.currentPackageId) {
      this.packageService.updateServicePackage(this.currentPackageId, val).subscribe(() => {
        this.resetPackageForm();
        this.loadPackages();
      });
    } else {
      this.packageService.addServicePackage(val).subscribe(() => {
        this.resetPackageForm();
        this.loadPackages();
      });
    }
  }

  editPackage(pkg: any): void {
    this.isEditingPackage = true;
    this.currentPackageId = pkg.id;
    this.packageForm.patchValue(pkg);
  }

  deletePackage(id: number): void {
    this.packageService.deleteServicePackage(id).subscribe(() => this.loadPackages());
  }

  resetPackageForm(): void {
    this.isEditingPackage = false;
    this.currentPackageId = undefined;
    this.packageForm.reset({ availabilityStatus: 'ACTIVE', categoryId: '' });
  }
}
