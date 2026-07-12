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

  constructor(private fb: FormBuilder, private vehicleService: VehicleService) {}

  ngOnInit(): void {
    this.initForm();
    this.load();
  }

  initForm(): void {
    const currentYear = new Date().getFullYear();
    this.vehicleForm = this.fb.group({
      registrationNumber: ['', Validators.required],
      manufacturer: ['', Validators.required],
      model: ['', Validators.required],
      fuelType: ['PETROL', Validators.required],
      year: [currentYear, [Validators.required, Validators.min(1950), Validators.max(currentYear + 1)]],
      color: ['', Validators.required]
    });
  }

  load(): void {
    this.vehicleService.getVehicles().subscribe({
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
    this.vehicleForm.reset({ fuelType: 'PETROL', year: new Date().getFullYear() });
    this.showForm = true;
  }

  openEdit(v: any): void {
    this.editing = v;
    this.vehicleForm.patchValue(v);
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
    
    if (this.editing) {
      this.vehicleService.updateVehicle(this.editing.id, this.vehicleForm.value).subscribe({
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
      // Mocking customer_id=1 for the demo since auth interceptor isn't strictly mapping user IDs
      const newVehicle = { ...this.vehicleForm.value, customerId: 1 };
      this.vehicleService.addVehicle(newVehicle).subscribe({
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
    this.vehicleService.deleteVehicle(Number(this.confirmId)).subscribe({
      next: () => {
        this.confirmId = null;
        this.load();
      },
      error: (err) => console.error('Failed to delete vehicle', err)
    });
  }
}
