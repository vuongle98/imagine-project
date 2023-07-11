import { NgModule } from '@angular/core';
import { NzImportModule } from '../nz-import.module';
import { LoadingComponent } from './components/loading/loading.component';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RowDirective } from './directives/row.directives';

const COMPONENTS = [LoadingComponent];

const DIRECTIVES = [RowDirective];

const MODULES = [FormsModule, ReactiveFormsModule]

@NgModule({
  declarations: [COMPONENTS, DIRECTIVES],
  imports: [CommonModule, NzImportModule],
  exports: [NzImportModule, COMPONENTS, MODULES, DIRECTIVES],
})
export class SharedModule {}
