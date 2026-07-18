import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ServiceListComponent } from './service-list/service-list.component';
import { ServiceBrowseComponent } from './service-browse/service-browse.component';

const routes: Routes = [
  { path: '', component: ServiceListComponent },
  { path: 'browse', component: ServiceBrowseComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServiceRoutingModule { }
