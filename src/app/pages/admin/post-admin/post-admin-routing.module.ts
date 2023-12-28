import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListAdminPostComponent } from './pages/list-admin-post/list-admin-post.component';
import { ListAdminCategoryComponent } from './pages/list-admin-category/list-admin-category.component';
import { ListAdminAnswerComponent } from '../quiz-admin/pages/list-admin-answer/list-admin-answer.component';

const routes: Routes = [
  {
    path: 'category',
    component: ListAdminCategoryComponent,
  },
  {
    path: '',
    component: ListAdminPostComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PostAdminRoutingModule {}
