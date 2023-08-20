import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import {
  BehaviorSubject,
  Observable,
  filter,
  map,
  of,
  shareReplay,
  tap,
} from 'rxjs';
import { LoginPayload, TokenResponse, User } from 'src/app/shared/models/user';
import { LoadingService } from 'src/app/shared/components/loading/loading.service';
import { AUTH_DATA } from '@shared/utils/constant';

@Injectable({
  providedIn: 'root',
})
export class AuthStore {
  private _token = new BehaviorSubject<string>('');
  token$ = this._token.asObservable();
  private _user = new BehaviorSubject<User>({} as User);
  user$ = this._user.asObservable();
  token = '';

  isLoggedIn$!: Observable<boolean>;
  isLoggedOut$!: Observable<boolean>;

  constructor(
    private authService: AuthService,
    private loadingService: LoadingService
  ) {
    this.isLoggedIn$ = this.user$.pipe(map((user) => !!user.id));

    this.isLoggedOut$ = this.isLoggedIn$.pipe(map((loggedIn) => !loggedIn));
  }

  /**
   * Verifies the user by calling the `verifyUser` method of the `authService` and performs some additional actions.
   *
   * @return {Observable<User>} An observable that emits the user object after performing some additional actions.
   */
  verifyUser(): Observable<User> {
    const userStr = localStorage.getItem(AUTH_DATA);

    if (!userStr) return of({} as User);

    const parsedUser: TokenResponse = JSON.parse(userStr);
    const token = parsedUser.type + ' ' + parsedUser.token;

    // emit token from localStorage to verify
    this._user.next(parsedUser.user);
    this._token.next(token);
    this.token = token;

    return this.authService.verifyUser().pipe(
      map((user) => {
        user.token = parsedUser.token;

        this._user.next(user);
        this._token.next(token);
        this.token = parsedUser.token;

        return user;
      })
    );
  }

  login(payload: LoginPayload) {
    // unsubscribe to previous ws session, other previous data
    return this.authService.getToken(payload).pipe(
      tap((loginResponse) => {
        const token = loginResponse.type + ' ' + loginResponse.token;
        const user = loginResponse.user;
        user.token = token;

        this._token.next(token);
        this._user.next(user);
        localStorage.setItem(AUTH_DATA, JSON.stringify(loginResponse));
      }),
      shareReplay()
    );
  }

  anonymousLogin(username: string) {
    username = 'anonymous-' + username.trim();
    this._user.next({ username, fullName: username } as User);
  }

  logout() {
    this._user.next({} as User);
    this._token.next('');
    localStorage.removeItem(AUTH_DATA);
  }
}
