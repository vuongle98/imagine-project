import { Injectable } from '@angular/core';
import { LoadingService } from '@shared/components/loading/loading.service';
import { User, UserQueryParam } from '@shared/models/user';
import { UserService } from '@shared/services/rest-api/user/user.service';
import { BehaviorSubject, Observable, of, tap } from 'rxjs';
import { BaseDataSource } from 'src/app/shared/datasource/base-datasource';
import { Pageable } from 'src/app/shared/models/utils';

@Injectable({
  providedIn: 'root',
})
export class UserAdminDataSource extends BaseDataSource<Pageable<User>> {
  override dataSubject: BehaviorSubject<Pageable<User>> = new BehaviorSubject(
    {} as Pageable<User>
  );

  constructor(
    private userService: UserService,
    private loadingService: LoadingService
  ) {
    super();
  }

  loadData(params: UserQueryParam) {
    if (!params?.page || !params?.size) {
      params.page = 0;
      params.size = 10;
    }

    const loadUser$ = this.userService
      .findUser(params)
      .pipe(tap((data) => this.dataSubject.next(data)));

    this.loadingService.showLoaderUntilCompleted(loadUser$).subscribe();
  }

  ngOnDestroy(): void {
    this.dataSubject.complete();
  }

  override create(user: User): Observable<User> {
    return this.userService.adminCreateUser(user);
  }

  override update(id: string, user: User): Observable<User> {
    return this.userService.adminUpdateUser(id, user);
  }

  override delete(id: string, forever = false): Observable<void> {
    return this.userService.adminDeleteUser(id, forever);
  }

  enable(id: string): Observable<User> {
    return this.userService.adminUpdateUser(id, { id, enabled: true });
  }

  disable(id: string): Observable<User> {
    return this.userService.adminUpdateUser(id, { id, enabled: false });
  }

  lock(id: string): Observable<User> {
    return this.userService.adminUpdateUser(id, { id, locked: true });
  }

  unlock(id: string): Observable<User> {
    return this.userService.adminUpdateUser(id, { id, locked: false });
  }
}
