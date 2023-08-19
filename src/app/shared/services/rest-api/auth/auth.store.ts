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
  private _user = new BehaviorSubject<User>({username: 'anonymous', fullName: 'anonymous'} as User);
  user$ = this._user.asObservable();
  token = '';

  isLoggedIn$!: Observable<boolean>;
  isLoggedOut$!: Observable<boolean>;

  constructor(
    private authService: AuthService,
    private loadingService: LoadingService,
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

    const parsedUser = JSON.parse(userStr);
    const token = parsedUser.type + ' ' + parsedUser.token;

    // emit token to verify
    this._user.next(parsedUser);
    this._token.next(token);
    this.token = token;

    return this.authService.verifyUser().pipe(
      tap((user) => {
        this._user.next(user);
        this._token.next(token);
        this.token = parsedUser.token;
      })
    );
  }

  login(payload: LoginPayload) {
    // unsubscribe to previous ws session, other previous data
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
