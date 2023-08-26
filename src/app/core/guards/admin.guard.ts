import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { PERMISSION } from '@shared/utils/constant';
import { Observable, iif, map, of } from 'rxjs';
import { AuthStore } from 'src/app/shared/services/rest-api/auth/auth.store';

export const adminGuard: CanActivateFn = (route, state) => {
  const autStore = inject(AuthStore);
  const router = inject(Router);
  return autStore.permission$.pipe(
    map((permission) => {
      if (permission.includes(PERMISSION.admin)) {
        return true;
      }

      return router.parseUrl('/auth/login');
    })
  );
};
