import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild,
  ViewRef,
} from '@angular/core';
import { Conversation } from '@shared/models/chat';
import { User } from '@shared/models/user';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { ChatService } from '@shared/services/rest-api/chat/chat.service';
import { RxStompService } from '@shared/services/rx-stomp/rx-stomp.service';
import { Observable, map, of, switchMap, take } from 'rxjs';

@Component({
  selector: 'app-list-chat',
  templateUrl: './list-chat.component.html',
  styleUrls: ['./list-chat.component.scss'],
})
export class ListChatComponent implements OnInit {
  @Input() currentUser!: any;

  @Output() closeChange = new EventEmitter<any>();

  currentChatInfo?: any;

  listChat: Conversation[] = [
    {
      name: 'public',
      id: 'public',
    },
  ];

  @ViewChild('appChat') appchat!: ElementRef;

  constructor(
    private chatService: ChatService,
    private authStore: AuthStore,
    private rxStompService: RxStompService
  ) {}

  ngOnInit(): void {
    console.log(this.currentUser);

    this.authStore.isLoggedIn$
      .pipe(
        switchMap((isLoggedIn) => {
          if (isLoggedIn) {
            return this.chatService
              .findConversations()
              .pipe(map((res) => res.content));
          }
          return of([]);
        }),
        take(1)
      )
      .subscribe((res) => {
        this.listChat = [...this.listChat, ...res];
      });
  }

  openChat(chat: Conversation) {
    this.currentChatInfo = chat;
  }

  closeChat() {
    this.currentChatInfo = undefined;
  }

  onClose() {
    this.closeChange.emit();
  }
}
