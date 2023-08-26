import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@shared/shared.module';
import { ListUserComponent } from './pages/list-user/list-user.component';
import { UserAdminRoutingModule } from './user-admin-routing.module';


const COMPONENTS = [
  ListUserComponent
];

@NgModule({
  declarations: [
    COMPONENTS
  ],
  imports: [
    CommonModule,
    SharedModule,
    UserAdminRoutingModule
  ]
})
export class UserAdminModule { }
