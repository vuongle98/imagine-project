import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { ModuleRoutingModule } from './pages-routing.module';

const COMPONENTS: any[] = [];
const COMPONENTS_DYNAMIC: any[] = [];

@NgModule({
  imports: [SharedModule, ModuleRoutingModule],
  declarations: [...COMPONENTS, ...COMPONENTS_DYNAMIC],
})
export class ModulesModule {}
