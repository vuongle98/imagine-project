import {
  Component,
  EventEmitter,
  Input,
  Output,
  ViewChild,
  ViewContainerRef,
} from '@angular/core';
import { RxStompService } from 'src/app/shared/services/rx-stomp/rx-stomp.service';
import { Message } from '@stomp/stompjs';
import { Subscription, filter, switchMap, takeUntil, tap } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthStore } from 'src/app/shared/services/rest-api/auth/auth.store';
import { MessageComponent } from '../message/message.component';
import { OnDestroyService } from '@shared/services/common/on-destroyed.service';
import { User } from '@shared/models/user';
import { ChatMessage } from '@shared/models/chat';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
  providers: [OnDestroyService],
})
export class ChatComponent {
  @Input() currentChatInfo!: any;
  @Input() currentUser!: any;

  receivedMessages: any[] = [];

  msgForm!: FormGroup;

  @ViewChild('messageContainer', { read: ViewContainerRef })
  viewContainerRef!: ViewContainerRef;

  constructor(
    private rxStompService: RxStompService,
    private fb: FormBuilder,
    private auth: AuthStore,
    private onDestroyService: OnDestroyService
  ) {
    this.initMsgForm();
  }

  initMsgForm() {
    this.msgForm = this.fb.group({
      message: [''],
    });
  }

  ngOnInit() {
    return this.rxStompService
      .watch(`/topic/${this.currentChatInfo.id}`)
      .pipe(takeUntil(this.onDestroyService))
      .subscribe((message: Message) => {
        const parseMessage = JSON.parse(message.body);

        if (parseMessage instanceof Array) {
          for (const mess of parseMessage) {
            this.pushMessage(this.currentUser, mess);
          }
        } else {
          this.pushMessage(this.currentUser, parseMessage);
        }
      });
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

  pushMessage(user: User, message: ChatMessage) {
    if (user.username !== message.sender?.username) {
      this.appendMessage(message, 'left');
    } else {
      this.appendMessage(message, 'right');
      this.msgForm.reset();
    }
  }

  appendMessage(message: ChatMessage, side: string) {
    const messageRef = this.viewContainerRef.createComponent(MessageComponent);
    messageRef.instance.message = message;
    messageRef.instance.side = side;
  }
}
