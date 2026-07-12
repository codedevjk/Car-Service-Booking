import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileViewComponent } from './profile-view/profile-view.component';


@NgModule({
  declarations: [
    ProfileViewComponent
  ],
  imports: [
    ReactiveFormsModule,
    CommonModule,
    ProfileRoutingModule
  ]
})
export class ProfileModule { }
