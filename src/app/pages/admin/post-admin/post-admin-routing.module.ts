import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListAdminPostComponent } from './pages/list-admin-post/list-admin-post.component';
import { ListAdminCategoryComponent } from './pages/list-admin-category/list-admin-category.component';

const routes: Routes = [
  {
    path: 'category',
    component: ListAdminCategoryComponent,
  },
  {
    path: '',
    component: ListAdminPostComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PostAdminRoutingModule {}
