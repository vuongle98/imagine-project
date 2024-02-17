import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FileAdminIndexComponent } from './pages/file-admin-index/file-admin-index.component';

const routes: Routes = [
  {
    path: '',
    component: FileAdminIndexComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FileAdminRoutingModule { }
