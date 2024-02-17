import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { Observable, iif, map, of } from 'rxjs';
import { AuthStore } from 'src/app/shared/services/rest-api/auth/auth.store';

export const logoutGuard: CanActivateFn = (route, state) => {
  const authStore = inject(AuthStore);
  const router = inject(Router);
  return authStore.isLoggedOut$.pipe(
    map((isLogout) => {
      if (isLogout) {
        return true;
      }

      return router.parseUrl('/');
    })
  );
};
