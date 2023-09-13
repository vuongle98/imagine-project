import { Component, Input } from '@angular/core';
import { ChatMessage } from '@shared/models/chat';
import { User } from 'src/app/shared/models/user';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {

  @Input() side = 'right';
  @Input() message!: ChatMessage;

  viewProfile() {
    console.log("okie");

  }

}
