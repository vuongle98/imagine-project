import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FileAdminRoutingModule } from './file-admin-routing.module';
import { FileAdminIndexComponent } from './pages/file-admin-index/file-admin-index.component';
import { SharedModule } from '@shared/shared.module';
import { FileAdminTableComponent } from './components/file-admin-table/file-admin-table.component';
import { FileAdminSearchFormComponent } from './components/file-admin-search-form/file-admin-search-form.component';
import { FileAdminCreateFormComponent } from './components/file-admin-create-form/file-admin-create-form.component';


@NgModule({
  declarations: [
    FileAdminIndexComponent,
    FileAdminTableComponent,
    FileAdminSearchFormComponent,
    FileAdminCreateFormComponent
  ],
  imports: [
    CommonModule,
    FileAdminRoutingModule,
    SharedModule
  ]
})
export class FileAdminModule { }
