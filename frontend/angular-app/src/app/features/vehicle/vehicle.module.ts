import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { VehicleRoutingModule } from './vehicle-routing.module';
import { VehicleListComponent } from './vehicle-list/vehicle-list.component';

@NgModule({
  declarations: [
    VehicleListComponent
  ],
  imports: [
    CommonModule,
    VehicleRoutingModule,
    ReactiveFormsModule
  ]
})
export class VehicleModule { }
