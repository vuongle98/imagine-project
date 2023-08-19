import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import {
  BehaviorSubject,
  Observable,
  filter,
  map,
  shareReplay,
  tap,
} from 'rxjs';
import { LoginPayload, TokenResponse, User } from 'src/app/shared/models/user';
import { LoadingService } from 'src/app/shared/components/loading/loading.service';

const AUTH_DATA = 'auth_data';

@Injectable({
  providedIn: 'root',
})
export class AuthStore {
  private _token = new BehaviorSubject<string>('');
  token$ = this._token.asObservable();
  private _user = new BehaviorSubject<User>({} as User);
  user$ = this._user.asObservable();

  isLoggedIn$!: Observable<boolean>;
  isLoggedOut$!: Observable<boolean>;

  constructor(
    private authService: AuthService,
    private loadingService: LoadingService
  ) {
    this.isLoggedIn$ = this.user$.pipe(map((user) => !!user.id));

    this.isLoggedOut$ = this.isLoggedIn$.pipe(map((loggedIn) => !loggedIn));

    const userStr = localStorage.getItem(AUTH_DATA);

    if (userStr) {
      const user = JSON.parse(userStr);

      // check token is valid


      this._user.next(user);
      this._token.next(user.type + ' ' + user.token);
    }
  }

  login(payload: LoginPayload) {
    return this.authService.getToken(payload).pipe(
      tap((loginResponse) => {
        this._token.next(loginResponse.type + ' ' + loginResponse.token);
        this._user.next(loginResponse);
        localStorage.setItem(AUTH_DATA, JSON.stringify(loginResponse));
      }),
      shareReplay()
    );
  }

  logout() {
    this._user.next({} as User);
    this._token.next('');
    localStorage.removeItem(AUTH_DATA);
  }
}
