import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { PagesRoutingModule } from './pages-routing.module';
import { CalculatorComponent } from './calculator/calculator.component';
import { PostIndexComponent } from './post/pages/post-index/post-index.component';
import { PostDetailComponent } from './post/pages/post-detail/post-detail.component';

const COMPONENTS: any[] = [];
const COMPONENTS_DYNAMIC: any[] = [];

@NgModule({
  imports: [SharedModule, PagesRoutingModule],
  declarations: [...COMPONENTS, ...COMPONENTS_DYNAMIC, CalculatorComponent, PostIndexComponent, PostDetailComponent],
})
export class PagesModule {}
