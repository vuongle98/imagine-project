import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserAdminDataSource } from '../../services/user-admin.datasource';
import { User } from '@shared/models/user';
import { DataType, TableAction } from '@shared/modules/table/table.component';

@Component({
  selector: 'app-admin-user-table',
  templateUrl: './admin-user-table.component.html',
  styleUrls: ['./admin-user-table.component.scss'],
})
export class AdminUserTableComponent {
  @Input() userAdminDataSource!: UserAdminDataSource;
  @Input() totalRows = 1;
  @Input() currentPage = 0;
  @Input() listUser: User[] = [];

  @Output() onEditUser = new EventEmitter<User>();
  @Output() onDeleteUser = new EventEmitter<User>();
  @Output() onLockUser = new EventEmitter<User>();
  @Output() onUnlockUser = new EventEmitter<User>();

  @Output() onDisableUser = new EventEmitter<User>();
  @Output() onEnableUser = new EventEmitter<User>();

  pageSize = 10;

  DataType = DataType;

  actions: TableAction[] = [
    {
      icon: 'edit',
      title: 'Edit',
      action: (item: User) => this.onEditUser.emit(item),
      show: (item: User) => true,
    },
    {
      icon: 'delete',
      title: 'Block',
      action: (item: User) => this.onLockUser.emit(item),
      show: (item: User) => !item.locked,
    },
    {
      icon: 'lock',
      title: 'Unlock',
      action: (item: User) => this.onUnlockUser.emit(item),
      show: (item: User) => !!item.locked,
    },
    {
      icon: 'delete',
      title: 'Disable',
      action: (item: User) => this.onDisableUser.emit(item),
      show: (item: User) => !!item.enabled,
    },
    {
      icon: 'delete',
      title: 'Enable',
      action: (item: User) => this.onEnableUser.emit(item),
      show: (item: User) => !item.enabled,
    },
  ];

  onPageChange(page: number) {
    this.currentPage = page;
    this.userAdminDataSource.loadData({
      page: this.currentPage,
      size: this.pageSize,
    });
  }
}
