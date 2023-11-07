import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { authGuard } from '@core/guards/auth.guard';
import { adminGuard } from '@core/guards/admin.guard';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
  },
  {
    path: 'quiz',
    loadChildren: () =>
      import('./quiz-admin/quiz-admin.module').then((m) => m.QuizAdminModule),
    canActivate: [authGuard, adminGuard],
  },
  {
    path: 'user',
    loadChildren: () =>
      import('./user/user-admin.module').then((m) => m.UserAdminModule),
    canActivate: [authGuard, adminGuard],
  },
  {
    path: 'post',
    loadChildren: () =>
      import('./post-admin/post-admin.module').then((m) => m.PostAdminModule),
    canActivate: [authGuard, adminGuard],
  },
  {
    path: 'file',
    loadChildren: () =>
      import('./file-admin/file-admin.module').then((m) => m.FileAdminModule),
    canActivate: [authGuard, adminGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
