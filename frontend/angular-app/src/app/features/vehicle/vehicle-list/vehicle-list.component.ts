import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { VehicleService } from 'src/app/core/services/vehicle.service';

@Component({
  selector: 'app-vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css']
})
export class VehicleListComponent implements OnInit {
  vehicles: any[] = [];
  search = '';
  page = 0;
  total = 0;
  size = 5;

  showForm = false;
  editing: any = null;
  confirmId: string | null = null;
  error: string | null = null;

  vehicleForm!: FormGroup;
  isSubmitting = false;

  constructor(private fb: FormBuilder, private vehicleService: VehicleService) { }

  ngOnInit(): void {
    this.initForm();
    this.load();
  }

  getUserId(): string {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr).userId : '';
  }

  initForm(): void {
    const currentYear = new Date().getFullYear();
    this.vehicleForm = this.fb.group({
      registrationNumber: ['', [Validators.required, Validators.pattern(/^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$/)]],
      manufacturer: ['', Validators.required],
      model: ['', Validators.required],
      fuelType: ['PETROL', Validators.required],
      manufacturingYear: [currentYear, [Validators.required, Validators.min(1950), Validators.max(currentYear + 1)]]
    });
  }

  load(): void {
    const userId = this.getUserId();
    if (!userId) return;
    this.vehicleService.getVehicles(userId).subscribe({
      next: (data) => {
        const lowerSearch = this.search.toLowerCase();
        this.vehicles = data.filter((v: any) =>
          v.registrationNumber.toLowerCase().includes(lowerSearch) ||
          v.manufacturer.toLowerCase().includes(lowerSearch)
        );
        this.total = this.vehicles.length;
      },
      error: (err) => console.error('Error fetching vehicles', err)
    });
  }

  onSearchChange(term: string): void {
    this.search = term;
    this.page = 0;
    this.load();
  }

  openNew(): void {
    this.editing = null;
    this.vehicleForm.reset({ fuelType: 'PETROL', manufacturingYear: new Date().getFullYear() });
    this.showForm = true;
  }

  openEdit(v: any): void {
    this.editing = v;
    // Strip hyphens so it passes the new strict regex pattern validation
    const vToPatch = {
      ...v,
      registrationNumber: v.registrationNumber ? v.registrationNumber.replace(/-/g, '') : ''
    };
    this.vehicleForm.patchValue(vToPatch);
    this.showForm = true;
  }

  closeForm(): void {
    this.showForm = false;
    this.error = null;
  }

  onSubmit(): void {
    if (this.vehicleForm.invalid) {
      this.vehicleForm.markAllAsTouched();
      return;
    }
    this.isSubmitting = true;
    const userId = this.getUserId();

    if (this.editing) {
      this.vehicleService.updateVehicle(this.editing.id, this.vehicleForm.value, userId).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.showForm = false;
          this.load();
        },
        error: (err) => {
          this.error = 'Failed to update vehicle';
          this.isSubmitting = false;
        }
      });
    } else {
      const newVehicle = { ...this.vehicleForm.value, userId };
      this.vehicleService.addVehicle(newVehicle, userId).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.showForm = false;
          this.load();
        },
        error: (err) => {
          this.error = 'Failed to add vehicle';
          this.isSubmitting = false;
        }
      });
    }
  }

  requestRemove(id: string): void {
    this.confirmId = id;
  }

  confirmRemove(): void {
    if (!this.confirmId) return;
    const userId = this.getUserId();
    this.vehicleService.deleteVehicle(Number(this.confirmId), userId).subscribe({
      next: () => {
        this.confirmId = null;
        this.load();
      },
      error: (err) => console.error('Failed to delete vehicle', err)
    });
  }

  // Formats KA01MN1122 to KA-01-MN-1122. If already formatted or invalid length, returns as is.
  formatRegNo(regNo: string): string {
    if (!regNo) return '';
    // If it already has hyphens, just return it
    if (regNo.includes('-')) return regNo;
    // If it exactly matches the 10 character pattern, add hyphens
    if (/^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$/i.test(regNo)) {
      return `${regNo.substring(0, 2)}-${regNo.substring(2, 4)}-${regNo.substring(4, 6)}-${regNo.substring(6, 10)}`;
    }
    return regNo;
  }
}
