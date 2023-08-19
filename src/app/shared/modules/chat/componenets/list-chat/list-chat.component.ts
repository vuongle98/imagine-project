import { Component, EventEmitter, Output } from '@angular/core';
import { User } from '@shared/models/user';
import { AuthService } from '@shared/services/rest-api/auth/auth.service';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { ChatService } from '@shared/services/rest-api/chat/chat.service';
import { BehaviorSubject, Observable } from 'rxjs';

@Component({
  selector: 'app-list-chat',
  templateUrl: './list-chat.component.html',
  styleUrls: ['./list-chat.component.scss']
})
export class ListChatComponent {

  @Output() closeChange = new EventEmitter<any>();

  currentChatInfo?: any;

  listChat = [{
    id: '1',
    name: 'chat1'
  }, {
    id: '2',
    name: 'chat2'
  },
  {
    id: '3',
    name: 'chat3'
  }]

  currentUser$!: Observable<User>;

  constructor(
    private chatService: ChatService,
    private authStore: AuthStore
  ) {
    this.chatService.findConversations().subscribe(res => {
      console.log(res);
      this.listChat = res.content;
    });

    this.currentUser$ = this.authStore.user$;
  }

  openChat(chat: any) {
    this.currentChatInfo = chat;
  }

  onClose() {
    this.closeChange.emit();
  }
}
