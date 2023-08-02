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
import { AnswerCorrectDirective } from './directives/quiz/answer-correct.directive';
import { MarkDirective } from './directives/mark.directive';
import { QuizLevelDirective } from './directives/quiz/quiz-level.directive';
import { SkeletonDirective } from './directives/quiz/skeleton.directive';
import { SecurePipe } from './pipes/secure.pipe';

const COMPONENTS = [
  LoadingComponent,
  CardComponent,
  CardTitleComponent,
  CardContentComponent,
  DividerComponent,
  QuizLevelDirective,
  SkeletonDirective,
];

const DIRECTIVES = [
  RowDirective,
  VgButtonDirective,
  VgCardDirective,
  AnswerCorrectDirective,
  MarkDirective,
];

const PIPES = [SecurePipe];

const MODULES = [FormsModule, ReactiveFormsModule];

@NgModule({
  declarations: [COMPONENTS, DIRECTIVES, PIPES],
  imports: [CommonModule, NzImportModule],
  exports: [NzImportModule, COMPONENTS, MODULES, DIRECTIVES, PIPES],
})
export class SharedModule {}
