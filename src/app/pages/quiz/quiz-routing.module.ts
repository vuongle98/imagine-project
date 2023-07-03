import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { QuizIndexComponent } from './pages/quiz-index/quiz-index.component';
import { QuizDetailComponent } from './pages/quiz-detail/quiz-detail.component';

const routes: Routes = [
  { path: '', component: QuizIndexComponent },
  { path: ':id', component: QuizDetailComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class QuizRoutingModule {}
