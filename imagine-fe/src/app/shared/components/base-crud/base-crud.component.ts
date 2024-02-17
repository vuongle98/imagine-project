import { Component, Inject, Type } from '@angular/core';
import { BaseDataSource } from '@shared/datasource/base-datasource';
import { DialogConfig } from '@shared/modules/dialog/dialog.config';
import { DialogService } from '@shared/modules/dialog/dialog.service';
import { ConfirmComponent } from '../dialog/confirm-delete/confirm.component';
import {
  Observable,
  catchError,
  finalize,
  iif,
  of,
  switchMap,
  tap,
} from 'rxjs';
import { User } from '@shared/models/user';
import { UserAdminDataSource } from 'src/app/pages/admin/user/services/user-admin.datasource';
import { NotificationService } from '@shared/services/common/notificaton.service';

@Component({
  selector: 'app-base-crud',
  templateUrl: './base-crud.component.html',
  styleUrls: ['./base-crud.component.scss'],
})
export abstract class BaseCrudComponent<T extends BaseDataSource<any>> {
  constructor(
    protected dialogService: DialogService,
    protected dataSource: BaseDataSource<any>,
    protected notificationService: NotificationService
  ) {}

  openCreate(
    component: Type<any>,
    config: Partial<DialogConfig>
  ): Observable<any> {
    return this.dialogService.open(component, config).afterClose();
  }

  openUpdate(
    component: Type<any>,
    config: Partial<DialogConfig>
  ): Observable<any> {
    return this.dialogService.open(component, config).afterClose();
  }

  handleCreateOrUpdate<Q extends User>(data: Q): Observable<any> {
    if (!data) return of();
    return iif(
      () => !!data.id,
      this.dataSource.update(data.id, data),
      this.dataSource.create(data)
    ).pipe(
      catchError((error) => {
        this.notificationService.error(
          'Thêm mới thất bại',
          error.message || error
        );
        return of(error);
      }),
      finalize(() => this.dataSource.loadData({ page: 0, size: 10 }))
    );
  }

  openDelete(
    title: string,
    description: string,
    data: any,
    askForce = false
  ): Observable<any> {
    return this.dialogService
      .open(ConfirmComponent, {
        header: title,
        data: {
          dialogData: { description },
          resData: data,
          askForce,
        },
      })
      .afterClose()
      .pipe(
        switchMap((result?: any) =>
          iif(
            () => !!result?.isConfirmed,
            this.dataSource.delete(result?.data?.id, result?.isForce),
            of()
          )
        ),
        catchError((error) => {
          this.notificationService.error(
            'Xóa thất bại',
            error.message || error
          );
          return of(error);
        }),
        finalize(() => this.dataSource.loadData({ page: 0, size: 10 }))
      );
  }

  openConfirm(title: string, description: string, data: any): Observable<any> {
    return this.dialogService
      .open(ConfirmComponent, {
        header: title,
        data: {
          dialogData: { description },
          resData: data,
        },
      })
      .afterClose();
  }
}
