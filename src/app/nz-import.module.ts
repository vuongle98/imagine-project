import { NgModule } from '@angular/core';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzStepsModule } from 'ng-zorro-antd/steps';


@NgModule({
  exports: [
    NzIconModule,
    NzCardModule,
    NzGridModule,
    NzButtonModule,
    NzBreadCrumbModule,
    NzSpinModule,
    NzStepsModule
  ]
})
export class NzImportModule { }
