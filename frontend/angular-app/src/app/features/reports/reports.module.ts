import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportsRoutingModule } from './reports-routing.module';
import { ReportsViewComponent } from './reports-view/reports-view.component';


import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ReportsViewComponent
  ],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    FormsModule
  ]
})
export class ReportsModule { }
