import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { authGuard } from '@core/guards/auth.guard';
import { BaseLayoutComponent } from '@shared/modules/layouts/base-layout/base-layout.component';
import { NoBaseLayoutComponent } from '@shared/modules/layouts/no-base-layout/no-base-layout.component';

const routes: Routes = [
  {
    path: '',
    component: BaseLayoutComponent,
    canActivate: [authGuard],
    canActivateChild: [authGuard],
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./pages/pages.module').then((m) => m.PagesModule),
      },
      {
        path: 'welcome',
        loadChildren: () =>
          import('./pages/welcome/welcome.module').then((m) => m.WelcomeModule),
      },
    ],
  },
  {
    path: 'auth',
    component: NoBaseLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./pages/auth/auth.module').then((m) => m.AuthModule),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
