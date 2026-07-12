import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from 'src/app/core/services/category.service';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {
  categories: any[] = [];
  search = '';
  page = 0;
  total = 0;
  size = 5;

  showForm = false;
  editing: any = null;
  confirmId: string | null = null;
  error: string | null = null;
  
  categoryForm!: FormGroup;
  isSubmitting = false;

  constructor(private fb: FormBuilder, private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.initForm();
    this.load();
  }

  initForm(): void {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      active: [true]
    });
  }

  load(): void {
    this.categoryService.getCategories().subscribe({
      next: (data) => {
        const lowerSearch = this.search.toLowerCase();
        this.categories = data.filter((c: any) => 
          c.name.toLowerCase().includes(lowerSearch) || 
          (c.description && c.description.toLowerCase().includes(lowerSearch))
        );
        this.total = this.categories.length;
      },
      error: (err) => console.error('Error fetching categories', err)
    });
  }

  onSearchChange(term: string): void {
    this.search = term;
    this.page = 0;
    this.load();
  }

  openNew(): void {
    this.editing = null;
    this.categoryForm.reset({ active: true });
    this.showForm = true;
  }

  openEdit(c: any): void {
    this.editing = c;
    this.categoryForm.patchValue(c);
    this.showForm = true;
  }

  closeForm(): void {
    this.showForm = false;
    this.error = null;
  }

  onSubmit(): void {
    if (this.categoryForm.invalid) {
      this.categoryForm.markAllAsTouched();
      return;
    }
    this.isSubmitting = true;
    
    // Note: The backend controller doesn't explicitly support update by ID right now.
    // It only has a POST /api/categories that creates a new one. 
    this.categoryService.addCategory(this.categoryForm.value).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.showForm = false;
        this.load();
      },
      error: (err) => {
        this.error = 'Failed to save category';
        this.isSubmitting = false;
      }
    });
  }

  requestRemove(id: string): void {
    this.confirmId = id;
  }

  confirmRemove(): void {
    if (!this.confirmId) return;
    this.categoryService.deleteCategory(Number(this.confirmId)).subscribe({
      next: () => {
        this.confirmId = null;
        this.load();
      },
      error: (err) => console.error('Failed to delete category', err)
    });
  }
}
