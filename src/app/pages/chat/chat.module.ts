import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChatRoutingModule } from './chat-routing.module';
import { ChatIndexComponent } from './pages/chat-index/chat-index.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { MessageComponent } from './components/message/message.component';

@NgModule({
  declarations: [ChatIndexComponent, MessageComponent],
  imports: [CommonModule, SharedModule, ChatRoutingModule],
})
export class ChatModule {}
