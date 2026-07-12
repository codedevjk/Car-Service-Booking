import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ServiceRoutingModule } from './service-routing.module';
import { ServiceListComponent } from './service-list/service-list.component';
import { ServiceBrowseComponent } from './service-browse/service-browse.component';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ServiceListComponent,
    ServiceBrowseComponent
  ],
  imports: [
    CommonModule,
    ServiceRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class ServiceModule { }
