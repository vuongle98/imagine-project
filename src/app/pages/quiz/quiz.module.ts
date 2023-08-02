import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuizRoutingModule } from './quiz-routing.module';
import { QuizIndexComponent } from './pages/quiz-index/quiz-index.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { QuestionItemComponent } from './components/question-item/question-item.component';
import { QuestionItemCustomComponent } from './components/question-item-custom/question-item-custom.component';
import { AnswerItemComponent } from './components/answer-item/answer-item.component';
import { QuizIntroComponent } from './pages/quiz-intro/quiz-intro.component';
import { QuizPlayingComponent } from './pages/quiz-playing/quiz-playing.component';


@NgModule({
  declarations: [
    QuizIndexComponent,
    QuestionItemComponent,
    QuestionItemCustomComponent,
    AnswerItemComponent,
    QuizIntroComponent,
    QuizPlayingComponent
  ],
  imports: [
    CommonModule,
    QuizRoutingModule,
    SharedModule
  ]
})
export class QuizModule { }
