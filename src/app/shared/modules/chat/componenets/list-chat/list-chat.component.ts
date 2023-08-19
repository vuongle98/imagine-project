import { Component, EventEmitter, Output } from '@angular/core';
import { Conversation } from '@shared/models/chat';
import { User } from '@shared/models/user';
import { AuthService } from '@shared/services/rest-api/auth/auth.service';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { ChatService } from '@shared/services/rest-api/chat/chat.service';
import { BehaviorSubject, Observable, map, of, switchMap } from 'rxjs';

@Component({
  selector: 'app-list-chat',
  templateUrl: './list-chat.component.html',
  styleUrls: ['./list-chat.component.scss'],
})
export class ListChatComponent {
  @Output() closeChange = new EventEmitter<any>();

  currentChatInfo?: any;

  listChat: Conversation[] = [
    {
      name: 'public',
      id: 'public',
    },
  ];

  currentUser$!: Observable<User>;

  constructor(private chatService: ChatService, private authStore: AuthStore) {
    this.authStore.isLoggedIn$
      .pipe(switchMap((isLoggedIn) => {
        if (isLoggedIn) {
          return this.chatService.findConversations().pipe(map(res => res.content))
        }

        return of([]);
      }))
      .subscribe((res) => {
        this.listChat = [...this.listChat, ...res];
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
