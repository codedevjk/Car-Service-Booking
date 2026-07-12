import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { CategoryRoutingModule } from './category-routing.module';
import { CategoryListComponent } from './category-list/category-list.component';


@NgModule({
  declarations: [
    CategoryListComponent
  ],
  imports: [
    ReactiveFormsModule,
    CommonModule,
    CategoryRoutingModule
  ]
})
export class CategoryModule { }
