import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  Renderer2,
  ViewChild,
  ViewContainerRef,
} from '@angular/core';
import { RxStompService } from 'src/app/shared/services/rx-stomp/rx-stomp.service';
import { BehaviorSubject, debounceTime, takeUntil, tap } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthStore } from 'src/app/shared/services/rest-api/auth/auth.store';
import { MessageComponent } from './message/message.component';
import { OnDestroyService } from '@shared/services/common/on-destroyed.service';
import { User } from '@shared/models/user';
import { ChatMessage } from '@shared/models/chat';
import { rxStompConfig } from '@shared/utils/rx-stomp-config';
import { ChattingService } from '@shared/services/common/chatting.service';
import { CHAT_INSTANCE } from '@shared/utils/constant';
import { Pageable } from '@shared/models/utils';
import { ChatDataSource } from './chat.datasource';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
  providers: [OnDestroyService],
})
export class ChatComponent implements OnDestroy {
  @Input() currentChatInfo!: any;
  @Input() currentUser!: User;
  @Input() isFullScreen = false;

  msgForm!: FormGroup;

  anonymousUserForm!: FormGroup;

  currentMessagePage = 0;
  isLastMessage = false;

  _currentMessage$ = new BehaviorSubject<ChatMessage[]>([]);
  currentMessage$ = this._currentMessage$.asObservable();

  @ViewChild('messageContainer', { read: ViewContainerRef })
  viewContainerRef!: ViewContainerRef;

  @ViewChild('lastMessage', { static: true }) lastMessage!: ElementRef;

  @ViewChild('firstMessage', { static: true }) firstMessage!: ElementRef;

  constructor(
    private fb: FormBuilder,
    private auth: AuthStore,
    private onDestroyService: OnDestroyService,
    private rxStompService: RxStompService,
    private chattingService: ChattingService,
    private chatDataSource: ChatDataSource
  ) {
    this.initMsgForm();
  }

  initMsgForm() {
    this.msgForm = this.fb.group({
      message: [''],
    });

    this.anonymousUserForm = this.fb.group({
      username: [''],
    });
  }

  ngOnInit() {
    this.chattingService
      .subcribeAll([
        `/user/topic/${this.currentChatInfo.id}`,
        `/topic/${this.currentChatInfo.id}`,
      ])
      .pipe(
        tap((message: Pageable<ChatMessage> | ChatMessage) =>
          this.processMessage(message)
        ),
        debounceTime(100),
        takeUntil(this.onDestroyService)
      )
      .subscribe(() => {
        this.scrollToBottom();
      });

    this.chatDataSource.dataSubject.subscribe((data) => {
      this.processMessage(data);
    });
  }

  onScroll(isScrollTo: boolean): void {
    if (this.isLastMessage || this.currentChatInfo.id === 'public') return;

    this.chatDataSource.loadData({
      conversationId: this.currentChatInfo.id,
      page: this.currentMessagePage,
      size: 20,
      sort: 'createdAt,desc',
    });
    // fetch more messages
  }

  ngOnDestroy(): void {
    console.log('ngOnDestroy');
    sessionStorage.removeItem(CHAT_INSTANCE);
  }

  anonymousLogin() {
    const username = this.anonymousUserForm.value.username;

    const config = rxStompConfig;

    if (config.connectHeaders) {
      config.connectHeaders['username'] = username;
      delete config.connectHeaders['Authorization'];
    }

    this.rxStompService.configure(config);

    // this.subscribeChannel();
  }

  attachFile() {
    console.log('attach file');
  }

  onSendMessage() {
    const message = this.msgForm.value.message;

    if (!message) {
      return;
    }

    const payload = {
      content: message,
    };

    this.rxStompService.publish({
      destination: '/app/chat/' + this.currentChatInfo.id,
      body: JSON.stringify(payload),
    });
  }

  processMessage(message: Pageable<ChatMessage> | ChatMessage) {
    if (!message) {
      return;
    }

    if (message.content instanceof Array) {
      for (let msg of message.content) {
        msg = msg as ChatMessage;
        this.pushMessageToTop(this.currentUser, msg);
      }

      this.currentMessagePage++;
      // @ts-ignore
      this.isLastMessage = message.last;
    } else {
      this.pushMessage(this.currentUser, message as ChatMessage);
    }
  }

  pushMessage(user: User, message: ChatMessage) {
    if (user.username !== message.sender?.username) {
      this.appendMessageUI(message, 'left');
    } else {
      this.appendMessageUI(message, 'right');
      this.msgForm.reset();
    }
  }

  pushMessageToTop(user: User, message: ChatMessage) {
    if (user.username !== message.sender?.username) {
      this.appendMessageToTop(message, 'left');
    } else {
      this.appendMessageToTop(message, 'right');
      this.msgForm.reset();
    }
  }

  appendMessageUI(message: ChatMessage, side: string) {
    const messageRef = this.viewContainerRef.createComponent(MessageComponent);
    messageRef.instance.message = message;
    messageRef.instance.side = side;
  }

  appendMessageToTop(message: ChatMessage, side: string) {
    const messageRef = this.viewContainerRef.createComponent(MessageComponent, {
      index: 0,
    });
    messageRef.instance.message = message;
    messageRef.instance.side = side;
  }

  scrollToTop() {
    this.firstMessage.nativeElement.scrollIntoView({
      behavior: 'smooth',
      block: 'start',
    });
  }

  scrollToBottom() {
    this.lastMessage.nativeElement.scrollIntoView({
      behavior: 'smooth',
      block: 'end',
    });
  }
}
