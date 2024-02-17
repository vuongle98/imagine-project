import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostDetailComponent } from './pages/post-detail/post-detail.component';
import { authGuard } from '@core/guards/auth.guard';
import { PostIndexComponent } from './pages/post-index/post-index.component';

const routes: Routes = [
  {
    path: '',
    component: PostIndexComponent,
    canActivate: [authGuard],
  },
  {
    path: 'detail/:id',
    component: PostDetailComponent,
    canActivate: [authGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PostRoutingModule {}
