import { Component, ViewChild, ViewContainerRef } from '@angular/core';
import { RxStompService } from 'src/app/shared/services/rx-stomp/rx-stomp.service';
import { Message } from '@stomp/stompjs';
import { Subscription, filter, tap } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MessageComponent } from '../../components/message/message.component';
import { AuthStore } from 'src/app/shared/services/rest-api/auth/auth.store';

@Component({
  selector: 'app-chat-index',
  templateUrl: './chat-index.component.html',
  styleUrls: ['./chat-index.component.scss'],
})
export class ChatIndexComponent {
  private topicSubscription!: Subscription;

  receivedMessages: any[] = [];

  msgForm!: FormGroup;

  @ViewChild('messageContainer', { read: ViewContainerRef })
  viewContainerRef!: ViewContainerRef;

  constructor(
    private rxStompService: RxStompService,
    private fb: FormBuilder,
    private auth: AuthStore
  ) {
    this.initMsgForm();
  }

  initMsgForm() {
    this.msgForm = this.fb.group({
      message: [''],
    });
  }

  ngOnInit() {
    this.topicSubscription = this.rxStompService
      .watch('/topic/public')
      .subscribe((message: Message) => {
        this.appendMessage('BOT', message.body, 'left');
      });
  }

  onSendMessage() {
    const message = this.msgForm.value.message;

    if (!message) {
      return;
    }

    this.auth.user$
      .pipe(
        tap((userInfo) => {
          this.appendMessage(userInfo.fullName, message, 'right');
          this.msgForm.reset();
        })
      )
      .subscribe();

    this.rxStompService.publish({
      destination: '/app/chat.sendStringMessage',
      body: message,
    });
  }

  appendMessage(name: string, message: string, side: string) {
    const messageRef = this.viewContainerRef.createComponent(MessageComponent);
    messageRef.instance.message = message;
    messageRef.instance.name = name;
    messageRef.instance.side = side;
  }

  ngOnDestroy() {
    this.topicSubscription.unsubscribe();
  }
}
