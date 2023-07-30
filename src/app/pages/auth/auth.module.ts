import { NgModule } from '@angular/core';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { AuthRoutingModule } from './auth-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';

const COMPONENTS: any[] = [];
const COMPONENTS_DYNAMIC: any[] = [];

@NgModule({
  imports: [AuthRoutingModule, SharedModule],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_DYNAMIC,
    LoginComponent,
    RegisterComponent,
  ],
})
export class AuthModule {}
