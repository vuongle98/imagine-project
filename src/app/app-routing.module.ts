import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/welcome' },
  {
    path: 'welcome',
    loadChildren: () =>
      import('./pages/welcome/welcome.module').then((m) => m.WelcomeModule),
  },
  {
    path: 'quiz',
    loadChildren: () =>
      import('./pages/quiz/quiz.module').then((m) => m.QuizModule),
  },
  {
    path: 'chat',
    loadChildren: () =>
      import('./pages/chat/chat.module').then((m) => m.ChatModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
