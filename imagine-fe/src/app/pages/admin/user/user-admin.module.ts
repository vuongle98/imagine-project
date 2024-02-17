import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@shared/shared.module';
import { ListUserComponent } from './pages/list-user/list-user.component';
import { UserAdminRoutingModule } from './user-admin-routing.module';
import { AdminSearchUserComponent } from './components/admin-search-user/admin-search-user.component';
import { AdminUserTableComponent } from './components/admin-user-table/admin-user-table.component';
import { AdminUserFormComponent } from './components/admin-user-form/admin-user-form.component';

const COMPONENTS = [
  ListUserComponent,
  AdminSearchUserComponent,
  AdminUserTableComponent,
  AdminUserFormComponent,
];

@NgModule({
  declarations: [COMPONENTS],
  imports: [CommonModule, SharedModule, UserAdminRoutingModule],
})
export class UserAdminModule {}
