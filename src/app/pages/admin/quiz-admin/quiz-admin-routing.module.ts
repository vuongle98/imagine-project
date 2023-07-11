import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListQuestionComponent } from './pages/list-question/list-question.component';
import { ListQuizComponent } from './pages/list-quiz/list-quiz.component';

const routes: Routes = [
  { path: 'question', component: ListQuestionComponent },
  { path: '', component: ListQuizComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class QuizAdminRoutingModule {}
