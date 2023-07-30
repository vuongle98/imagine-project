import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { Observable, iif, map, of } from 'rxjs';
import { AuthStore } from 'src/app/shared/services/rest-api/auth/auth.store';

export const authGuard: CanActivateFn = (route, state) => {
  const autStore = inject(AuthStore);
  const router = inject(Router);
  return autStore.isLoggedIn$.pipe(
    map((isLogin) => {
      if (isLogin) {
        return true;
      }

      return router.parseUrl('/auth/login');
    })
  );
};
