import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatIndexComponent } from './pages/chat-index/chat-index.component';

const routes: Routes = [
  {
    path: '',
    component: ChatIndexComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ChatRoutingModule { }
