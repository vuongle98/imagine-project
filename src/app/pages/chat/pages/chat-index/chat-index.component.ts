import { Component } from '@angular/core';
import { RxStompService } from 'src/app/shared/services/rx-stomp/rx-stomp.service';
import { Message } from '@stomp/stompjs';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-chat-index',
  templateUrl: './chat-index.component.html',
  styleUrls: ['./chat-index.component.scss'],
})
export class ChatIndexComponent {

  private topicSubscription!: Subscription;

  receivedMessages: any[] = [];

  constructor(private rxStompService: RxStompService) {}

  ngOnInit() {
    this.topicSubscription = this.rxStompService.watch('/topic/public').subscribe((message: Message) => {
      console.log(message.body);

      this.receivedMessages.push(message.body);
    });
  }

  onSendMessage() {
    const message = `Message generated at ${new Date()}`;
    this.rxStompService.publish({ destination: '/app/chat.sendStringMessage', body: message });
  }

  ngOnDestroy() {
    this.topicSubscription.unsubscribe();
  }
}
