import { Component, OnDestroy } from '@angular/core';
import { Modal } from '@shared/models/modal.model';
import { User } from '@shared/models/user';
import { ChattingService } from '@shared/services/common/chatting.service';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';

@Component({
  selector: 'app-sticky-button-chat',
  templateUrl: './sticky-button-chat.component.html',
  styleUrls: ['./sticky-button-chat.component.scss'],
})
export class StickyButtonChatComponent implements OnDestroy {
  currentUser!: User;
  isShowChat = false;
  initChat?: any;

  constructor(
    public auth: AuthStore,
    private chattingService: ChattingService
  ) {}

  toggleChat() {
    this.isShowChat = !this.isShowChat;
  }

  onInjectInputs(inputs: any): void {
    this.isShowChat = inputs?.isShowChat;
  }

  closeChat() {
    this.chattingService.closeChat();
  }

  ngOnDestroy(): void {
    console.log('ngOnDestroy');
  }
}
