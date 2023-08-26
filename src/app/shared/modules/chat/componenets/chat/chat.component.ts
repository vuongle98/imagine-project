import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnDestroy,
  Output,
  ViewChild,
  ViewContainerRef,
  inject,
} from '@angular/core';
import { RxStompService } from 'src/app/shared/services/rx-stomp/rx-stomp.service';
import { Message } from '@stomp/stompjs';
import {
  Subscription,
  combineLatest,
  debounceTime,
  filter,
  merge,
  of,
  switchMap,
  take,
  takeUntil,
  tap,
} from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthStore } from 'src/app/shared/services/rest-api/auth/auth.store';
import { MessageComponent } from '../message/message.component';
import { OnDestroyService } from '@shared/services/common/on-destroyed.service';
import { User } from '@shared/models/user';
import { ChatMessage } from '@shared/models/chat';
import { rxStompConfig } from '@shared/utils/rx-stomp-config';
import { RxStompState } from '@stomp/rx-stomp';
import { ChattingService } from '@shared/services/common/chatting.service';
import { CHAT_INSTANCE } from '@shared/utils/constant';

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

  receivedMessages: any[] = [];

  msgForm!: FormGroup;

  anonymousUserForm!: FormGroup;

  @ViewChild('messageContainer', { read: ViewContainerRef })
  viewContainerRef!: ViewContainerRef;

  @ViewChild('lastMessage', { static: true }) lastMessage!: ElementRef;

  constructor(
    private fb: FormBuilder,
    private auth: AuthStore,
    private onDestroyService: OnDestroyService,
    private rxStompService: RxStompService,
    private chattingService: ChattingService
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
        tap((message: ChatMessage | ChatMessage[]) =>
          this.processMessage(message)
        ),
        debounceTime(100),
        takeUntil(this.onDestroyService)
      )
      .subscribe(() => {
        this.scrollToBottom();
      });
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

  processMessage(message: ChatMessage | ChatMessage[]) {
    if (!message) {
      return;
    }

    if (message instanceof Array) {
      for (const mess of message) {
        this.pushMessage(this.currentUser, mess);
      }
    } else {
      this.pushMessage(this.currentUser, message);
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

  appendMessageUI(message: ChatMessage, side: string) {
    const messageRef = this.viewContainerRef.createComponent(MessageComponent);
    messageRef.instance.message = message;
    messageRef.instance.side = side;
  }

  scrollToBottom() {
    this.lastMessage.nativeElement.scrollIntoView({
      behavior: 'smooth',
      block: 'end',
    });
  }
}
