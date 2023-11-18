import { Injectable, OnDestroy } from '@angular/core';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { RxStompService } from '@shared/services/rx-stomp/rx-stomp.service';
import {
  Observable,
  Subject,
  filter,
  iif,
  of,
  switchMap,
  take,
  takeUntil,
} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class StartupService implements OnDestroy {
  private _destroy$ = new Subject<void>();

  constructor(
    private authStore: AuthStore,
    private rxStompService: RxStompService
  ) {}

  load() {
    // verify user --> get user info
    // subscribe ws channel
    // TODO: unauthenticated user
    this.authStore
      .verifyUser()
      .pipe(
        switchMap((user) =>
          iif(() => !!user.username, this.loggedInFlow(), of(null))
        )
      )
      .subscribe();
  }

  loggedInFlow(): Observable<any> {
    return this.authStore.user$.pipe(
      filter((user) => !!user.username),
      switchMap((user) => {
        this.rxStompService.configWs(undefined, user.token);
        this.rxStompService.activate();
        return of(user);
      }),
      takeUntil(this._destroy$)
    );
  }

  ngOnDestroy(): void {
    this._destroy$.next();
    this._destroy$.complete();
  }

  unauthenticatedFlow(username: string): Observable<any> {
    // fakeuser
    this.authStore.anonymousLogin(username);

    // config unauthenticated stomp user
    this.rxStompService.configWs(username, undefined);
    this.rxStompService.activate();
    return of(null);
  }
}
