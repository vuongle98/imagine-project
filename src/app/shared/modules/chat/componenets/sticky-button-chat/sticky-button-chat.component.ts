import { Component } from '@angular/core';

@Component({
  selector: 'app-sticky-button-chat',
  templateUrl: './sticky-button-chat.component.html',
  styleUrls: ['./sticky-button-chat.component.scss']
})
export class StickyButtonChatComponent {

  isShowChat = false;

  toggleChat() {
    this.isShowChat = !this.isShowChat;
  }

}
