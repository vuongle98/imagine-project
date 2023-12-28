import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListQuestionComponent } from './pages/list-question/list-question.component';
import { ListQuizComponent } from './pages/list-quiz/list-quiz.component';
import { ListAdminAnswerComponent } from './pages/list-admin-answer/list-admin-answer.component';

const routes: Routes = [
  { path: 'question', component: ListQuestionComponent },
  { path: '', component: ListQuizComponent },
  {
    path: 'answer',
    component: ListAdminAnswerComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class QuizAdminRoutingModule {}
