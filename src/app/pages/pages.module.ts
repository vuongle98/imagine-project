import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { PagesRoutingModule } from './pages-routing.module';
import { CalculatorComponent } from './calculator/calculator.component';

const COMPONENTS: any[] = [];
const COMPONENTS_DYNAMIC: any[] = [];

@NgModule({
  imports: [SharedModule, PagesRoutingModule],
  declarations: [...COMPONENTS, ...COMPONENTS_DYNAMIC, CalculatorComponent],
})
export class PagesModule {}
