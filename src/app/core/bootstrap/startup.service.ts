import { Injectable } from '@angular/core';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { RxStompService } from '@shared/services/rx-stomp/rx-stomp.service';
import { Observable, iif, of, switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class StartupService {
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
      .subscribe((res) => {
        console.log(res);
      });
  }

  loggedInFlow(): Observable<any> {
    return this.authStore.user$.pipe(
      switchMap((user) => {
        this.rxStompService.configWs(undefined, user.token);
        this.rxStompService.activate();
        return of(user);
      })
    );
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
