import { NgModule } from '@angular/core';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzStepsModule } from 'ng-zorro-antd/steps';
import { NzTypographyModule } from 'ng-zorro-antd/typography';
import { NzSpaceModule } from 'ng-zorro-antd/space';
import { NzCarouselModule } from 'ng-zorro-antd/carousel';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzModalModule } from 'ng-zorro-antd/modal';


@NgModule({
  exports: [
    NzIconModule,
    NzCardModule,
    NzGridModule,
    NzButtonModule,
    NzBreadCrumbModule,
    NzSpinModule,
    NzStepsModule,
    NzTypographyModule,
    NzSpaceModule,
    NzCarouselModule,
    NzDividerModule,
    NzRadioModule,
    NzCheckboxModule,
    NzModalModule
  ]
})
export class NzImportModule { }
