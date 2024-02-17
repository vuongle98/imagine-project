import { Component, ViewChild } from '@angular/core';
import { BaseCrudComponent } from '@shared/components/base-crud/base-crud.component';
import { UserAdminDataSource } from '../../services/user-admin.datasource';
import { DialogService } from '@shared/modules/dialog/dialog.service';
import { AdminSearchUserComponent } from '../../components/admin-search-user/admin-search-user.component';
import { filter, finalize, iif, of, switchMap, tap } from 'rxjs';
import { User } from '@shared/models/user';
import { AdminUserFormComponent } from '../../components/admin-user-form/admin-user-form.component';
import { NotificationService } from '@shared/services/common/notificaton.service';
import { ConfirmComponent } from '@shared/components/dialog/confirm-delete/confirm.component';

export enum UPDATE_TYPE {
  DISABLE = 'DISABLE',
  ENABLE = 'ENABLE',
  LOCK = 'LOCK',
  UNLOCK = 'UNLOCK',
}

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.scss'],
})
export class ListUserComponent extends BaseCrudComponent<UserAdminDataSource> {
  listUser: User[] = [];
  totalRows = 1;
  currentPage = 0;

  UPDATE_TYPE = UPDATE_TYPE;

  @ViewChild(AdminSearchUserComponent)
  adminSearchUserComponent!: AdminSearchUserComponent;

  constructor(
    protected override dialogService: DialogService,
    private userDataSource: UserAdminDataSource,
    protected override notificationService: NotificationService
  ) {
    console.log('ooo');
    super(dialogService, userDataSource, notificationService);
  }

  ngOnInit() {
    this.userDataSource.loadData({
      page: 0,
      size: 10,
    });

    this.userDataSource.dataSubject
      .pipe(filter((data) => !!data.content))
      .subscribe((res) => {
        this.listUser = res.content;
        this.totalRows = res.totalElements;
      });
  }

  ngAfterViewInit(): void {
    this.adminSearchUserComponent.emitSearch
      .pipe(
        tap((value) => {
          this.currentPage = 0;
          this.userDataSource.loadData({
            ...value,
            page: this.currentPage,
          });
        })
      )
      .subscribe((value) => {
        console.log(value);
      });

    this.adminSearchUserComponent.emitCreate
      .pipe(
        switchMap(() =>
          this.openCreate(AdminUserFormComponent, { header: 'Create user' })
        ),
        switchMap((formValue: User) =>
          this.handleCreateOrUpdate<User>(formValue)
        )
      )
      .subscribe(() => {
        console.log('create');
      });
  }

  openEditUser(user: User) {
    this.openUpdate(AdminUserFormComponent, {
      header: 'Edit user',
      data: user,
    })
      .pipe(
        switchMap((formValue) => this.handleCreateOrUpdate<User>(formValue))
      )
      .subscribe();
  }

  deleteUser(user: User) {
    this.openDelete(
      'Confirm delete user',
      'Do you sure you want to delete this user?',
      user
    ).subscribe();
  }

  handleUpdate(user: User, type: UPDATE_TYPE) {
    this.openConfirm(
      `Confirm ${type} user`,
      `Do you want to ${type} this user?`,
      user
    )
      .pipe(
        switchMap((result) => {
          if (!result?.isConfirmed) {
            return of(null);
          }

          switch (type) {
            case UPDATE_TYPE.ENABLE:
              return this.userDataSource.enable(user.id);
            case UPDATE_TYPE.DISABLE:
              return this.userDataSource.disable(user.id);
            case UPDATE_TYPE.LOCK:
              return this.userDataSource.lock(user.id);
            case UPDATE_TYPE.UNLOCK:
              return this.userDataSource.unlock(user.id);
          }
        }),
        finalize(() => this.userDataSource.loadData({ page: 0, size: 10 }))
      )
      .subscribe();
  }
}
