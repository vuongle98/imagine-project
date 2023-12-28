import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuizAdminRoutingModule } from './quiz-admin-routing.module';
import { ListQuestionComponent } from './pages/list-question/list-question.component';
import { ListQuizComponent } from './pages/list-quiz/list-quiz.component';
import { QuizAdminTableComponent } from './components/quiz-admin-table/quiz-admin-table.component';
import { QuestionAdminTableComponent } from './components/question-admin-table/question-admin-table.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { AdminSearchQuestionComponent } from './components/admin-search-question/admin-search-question.component';
import { AdminSearchQuizComponent } from './components/admin-search-quiz/admin-search-quiz.component';
import { QuestionFormComponent } from './components/question-form/question-form.component';
import { QuestionSelectComponent } from './components/quiz-form/question-select/question-select.component';
import { QuizFormComponent } from './components/quiz-form/quiz-form.component';
import { ListAdminAnswerComponent } from './pages/list-admin-answer/list-admin-answer.component';
import { AdminSearchAnswerComponent } from './components/admin-search-answer/admin-search-answer.component';
import { AnswerFormComponent } from './components/answer-form/answer-form.component';
import { AnswerAdminTableComponent } from './components/answer-admin-table/answer-admin-table.component';

@NgModule({
  declarations: [
    ListQuestionComponent,
    ListQuizComponent,
    ListAdminAnswerComponent,
    QuizAdminTableComponent,
    QuestionAdminTableComponent,
    AdminSearchQuestionComponent,
    AdminSearchQuizComponent,
    QuizFormComponent,
    QuestionFormComponent,
    QuestionSelectComponent,
    AdminSearchAnswerComponent,
    AnswerFormComponent,
    AnswerAdminTableComponent
  ],
  imports: [CommonModule, QuizAdminRoutingModule, SharedModule],
})
export class QuizAdminModule {}
