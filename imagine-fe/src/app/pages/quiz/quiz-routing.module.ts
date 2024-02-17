import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { QuizIndexComponent } from './pages/quiz-index/quiz-index.component';
import { QuizPlayingComponent } from './pages/quiz-playing/quiz-playing.component';
import { QuizIntroComponent } from './pages/quiz-intro/quiz-intro.component';

const routes: Routes = [
  { path: '', component: QuizIndexComponent },
  { path: 'playing/:id', component: QuizPlayingComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class QuizRoutingModule {}
