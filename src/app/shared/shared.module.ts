import { NgModule } from '@angular/core';
import { NzImportModule } from '../nz-import.module';
import { LoadingComponent } from './components/loading/loading.component';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RowDirective } from './directives/row.directives';
import { CardComponent } from './components/card/card.component';
import { CardTitleComponent } from './components/card/card-title/card-title.component';
import { CardContentComponent } from './components/card/card-content/card-content.component';
import { DividerComponent } from './components/divider/divider.component';
import { VgButtonDirective } from './directives/vg-button.directive';
import { VgCardDirective } from './directives/vg-card.directive';
import { AnswerCorrectDirective } from './directives/answer-correct.directive';
import { MarkDirective } from './directives/mark.directive';

const COMPONENTS = [
  LoadingComponent,
  CardComponent,
  CardTitleComponent,
  CardContentComponent,
  DividerComponent,
];

const DIRECTIVES = [
  RowDirective,
  VgButtonDirective,
  VgCardDirective,
  AnswerCorrectDirective,
  MarkDirective,
];

const MODULES = [FormsModule, ReactiveFormsModule];

@NgModule({
  declarations: [COMPONENTS, DIRECTIVES],
  imports: [CommonModule, NzImportModule],
  exports: [NzImportModule, COMPONENTS, MODULES, DIRECTIVES],
})
export class SharedModule {}
