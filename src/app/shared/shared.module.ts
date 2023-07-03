import { NgModule } from '@angular/core';
import { NzImportModule } from '../nz-import.module';
import { LoadingComponent } from './components/loading/loading.component';
import { CommonModule } from '@angular/common';

const COMPONENTS = [LoadingComponent];

@NgModule({
  declarations: [COMPONENTS],
  imports: [CommonModule, NzImportModule],
  exports: [NzImportModule, COMPONENTS],
})
export class SharedModule {}
