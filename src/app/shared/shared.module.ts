import { NgModule } from '@angular/core';
import { NzImportModule } from '../nz-import.module';
import { LoadingComponent } from './components/loading/loading.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

const COMPONENTS = [LoadingComponent];

const MODULES = [FormsModule]

@NgModule({
  declarations: [COMPONENTS],
  imports: [CommonModule, NzImportModule],
  exports: [NzImportModule, COMPONENTS, MODULES],
})
export class SharedModule {}
