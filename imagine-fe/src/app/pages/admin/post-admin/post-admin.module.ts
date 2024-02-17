import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PostAdminRoutingModule } from './post-admin-routing.module';
import { ListAdminPostComponent } from './pages/list-admin-post/list-admin-post.component';
import { ListAdminCategoryComponent } from './pages/list-admin-category/list-admin-category.component';
import { AdminSearchPostFormComponent } from './components/admin-search-post-form/admin-search-post-form.component';
import { AdminCreatePostFormComponent } from './components/admin-create-post-form/admin-create-post-form.component';
import { AdminCreateCategoryFormComponent } from './components/admin-create-category-form/admin-create-category-form.component';
import { AdminCreateCommentFormComponent } from './components/admin-create-comment-form/admin-create-comment-form.component';
import { AdminSearchCategoryFormComponent } from './components/admin-search-category-form/admin-search-category-form.component';
import { AdminPostTableComponent } from './components/admin-post-table/admin-post-table.component';
import { AdminCategoryTableComponent } from './components/admin-category-table/admin-category-table.component';
import { SharedModule } from '@shared/shared.module';
import { AdminPostDetailComponent } from './components/admin-post-detail/admin-post-detail.component';


@NgModule({
  declarations: [
    ListAdminPostComponent,
    ListAdminCategoryComponent,
    AdminSearchPostFormComponent,
    AdminCreatePostFormComponent,
    AdminCreateCategoryFormComponent,
    AdminCreateCommentFormComponent,
    AdminSearchCategoryFormComponent,
    AdminPostTableComponent,
    AdminCategoryTableComponent,
    AdminPostDetailComponent
  ],
  imports: [
    CommonModule,
    PostAdminRoutingModule,
    SharedModule
  ]
})
export class PostAdminModule { }
