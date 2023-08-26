import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '@shared/models/user';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { Observable, iif, map, of, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent {
  viewProfileUser$!: Observable<User>;

  constructor(
    public authStore: AuthStore,
    private activateRoute: ActivatedRoute,
    private router: Router
  ) {
    this.viewProfileUser$ = this.activateRoute.data.pipe(
      switchMap((data) =>
        iif(() => !!data['user'], of(data['user']), authStore.user$)
      )
    );
  }

  checkIsFriend(username: string): Observable<boolean> {
    return this.authStore.user$.pipe(
      map(
        (user) =>
          user.friends?.findIndex((f) => f.username === username) !== -1 ||
          username === user.username
      )
    );
  }

  logout() {
    this.authStore.logout();
    this.router.navigateByUrl('/auth/login');
  }
}
