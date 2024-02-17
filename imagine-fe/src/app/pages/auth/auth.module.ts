import { NgModule } from '@angular/core';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { AuthRoutingModule } from './auth-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ProfileComponent } from './pages/profile/profile.component';
import { FriendComponent } from './pages/profile/friend/friend.component';

const COMPONENTS: any[] = [];
const COMPONENTS_DYNAMIC: any[] = [];

@NgModule({
  imports: [AuthRoutingModule, SharedModule],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_DYNAMIC,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    FriendComponent,
  ],
})
export class AuthModule {}
