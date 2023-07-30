import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuizAdminRoutingModule } from './quiz-admin-routing.module';
import { CreateQuizComponent } from './components/create-quiz/create-quiz.component';
import { ListQuestionComponent } from './pages/list-question/list-question.component';
import { ListQuizComponent } from './pages/list-quiz/list-quiz.component';
import { QuizAdminTableComponent } from './components/quiz-admin-table/quiz-admin-table.component';
import { QuestionAdminTableComponent } from './components/question-admin-table/question-admin-table.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { AdminSearchQuestionComponent } from './components/admin-search-question/admin-search-question.component';
import { AdminSearchQuizComponent } from './components/admin-search-quiz/admin-search-quiz.component';


@NgModule({
  declarations: [
    CreateQuizComponent,
    ListQuestionComponent,
    ListQuizComponent,
    QuizAdminTableComponent,
    QuestionAdminTableComponent,
    AdminSearchQuestionComponent,
    AdminSearchQuizComponent
  ],
  imports: [
    CommonModule,
    QuizAdminRoutingModule,
    SharedModule
  ]
})
export class QuizAdminModule { }
