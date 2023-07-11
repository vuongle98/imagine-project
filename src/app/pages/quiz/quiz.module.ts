import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuizRoutingModule } from './quiz-routing.module';
import { QuizIndexComponent } from './pages/quiz-index/quiz-index.component';
import { QuizDetailComponent } from './pages/quiz-detail/quiz-detail.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { QuestionItemComponent } from './components/question-item/question-item.component';
import { QuestionItemCustomComponent } from './components/question-item-custom/question-item-custom.component';
import { AnswerItemComponent } from './components/answer-item/answer-item.component';


@NgModule({
  declarations: [
    QuizIndexComponent,
    QuizDetailComponent,
    QuestionItemComponent,
    QuestionItemCustomComponent,
    AnswerItemComponent
  ],
  imports: [
    CommonModule,
    QuizRoutingModule,
    SharedModule
  ]
})
export class QuizModule { }
