import { Component } from '@angular/core';
import { User } from '@shared/models/user';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';

@Component({
  selector: 'app-sticky-button-chat',
  templateUrl: './sticky-button-chat.component.html',
  styleUrls: ['./sticky-button-chat.component.scss'],
})
export class StickyButtonChatComponent {

  currentUser!:User;
  isShowChat = false;
  constructor(public auth: AuthStore) {
  }

  toggleChat() {
    this.isShowChat = !this.isShowChat;
  }
}
