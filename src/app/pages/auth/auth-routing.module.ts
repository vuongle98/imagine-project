import { NgModule, inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  ResolveFn,
  RouterModule,
  RouterStateSnapshot,
  Routes,
} from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { logoutGuard } from '@core/guards/logout.guard';
import { authGuard } from '@core/guards/auth.guard';
import { AuthService } from '@shared/services/rest-api/auth/auth.service';
import { UserService } from '@shared/services/rest-api/user/user.service';
import { catchError, of } from 'rxjs';
import { MessageService } from '@shared/services/common/message.service';

export const userResolver: ResolveFn<any> = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const authService = inject(UserService);
  const messageService = inject(MessageService);

  const username = route.paramMap.get('username');
  if (username) return authService.getProfile(username).pipe(
    catchError((err) => {
      messageService.displayError(err);

      return of(null);
    })
  );

  return of(null);
};

const routes: Routes = [
  {
    path: '',
    redirectTo: '/auth/login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [logoutGuard],
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [logoutGuard],
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [authGuard],
    resolve: {
      user: userResolver,
    }
  },
  {
    path: 'profile/:username',
    component: ProfileComponent,
    canActivate: [authGuard],
    resolve: {
      user: userResolver,
    }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule {}
