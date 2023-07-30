import {
  HTTP_INTERCEPTORS,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {
  Observable,
  catchError,
  filter,
  mergeMap,
  switchMap,
  take,
  takeUntil,
  tap,
  throwError,
} from 'rxjs';
import { AuthService } from 'src/app/shared/services/rest-api/auth/auth.service';
import { AuthStore } from 'src/app/shared/services/rest-api/auth/auth.store';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authStore: AuthStore, private router: Router) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const logoutHandler = () => {
      if (req.url.includes('/api/auth/logout')) {
        this.router.navigateByUrl('/auth/login');
      }
    };

    let authReq = req.clone();

    return this.authStore.token$.pipe(
      take(1),
      tap(() => logoutHandler()),
      mergeMap((token) => {
        if (token) {
          authReq = req.clone({ setHeaders: { Authorization: token } });
        }
        return next.handle(authReq).pipe(tap(() => logoutHandler()));
      })
    );
  }
}

export const JWTInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: JwtInterceptor,
  multi: true,
};
