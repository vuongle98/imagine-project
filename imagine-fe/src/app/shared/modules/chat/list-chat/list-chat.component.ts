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
import { ChattingService } from '@shared/services/common/chatting.service';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { ConversationService } from '@shared/services/rest-api/chat/conversation.service';
import { RxStompService } from '@shared/services/rx-stomp/rx-stomp.service';
import { CHAT_INSTANCE } from '@shared/utils/constant';
import { Observable, map, of, skip, switchMap, take } from 'rxjs';

@Component({
  selector: 'app-list-chat',
  templateUrl: './list-chat.component.html',
  styleUrls: ['./list-chat.component.scss'],
})
export class ListChatComponent implements OnInit {
  @Input() currentUser!: any;
  @Input() isFullScreen = false;

  @Output() closeChange = new EventEmitter<any>();

  @Input() currentChatInfo?: any;

  listChat: Conversation[] = [
    {
      title: 'public',
      id: 'public',
    },
  ];

  @ViewChild('appChat') appchat!: ElementRef;

  constructor(
    private conversationService: ConversationService,
    private authStore: AuthStore,
    private chattingService: ChattingService
  ) {}

  ngOnInit(): void {
    console.log(this.currentUser);

    this.authStore.isLoggedIn$
      .pipe(
        switchMap((isLoggedIn) => {
          if (isLoggedIn) {
            return this.conversationService
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
    const chat_instance = sessionStorage.getItem(CHAT_INSTANCE);
    if (!chat_instance) {
      sessionStorage.setItem(CHAT_INSTANCE, 'true');
      this.currentChatInfo = chat;
    } else {
      this.currentChatInfo = undefined;
    }
  }

  closeChat() {
    this.currentChatInfo = undefined;
  }

  onClose() {
    this.closeChange.emit();
  }
}
