import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'quiz',
    loadChildren: () =>
      import('./quiz/quiz.module').then(
        (m) => m.QuizModule
      ),
  },
  {
    path: 'chat',
    loadChildren: () =>
      import('./chat/chat.module').then(
        (m) => m.ChatModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModuleRoutingModule {}
