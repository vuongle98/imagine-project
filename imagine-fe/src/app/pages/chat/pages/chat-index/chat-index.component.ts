import {
  Component,
  OnDestroy,
  ViewChild,
  ViewContainerRef,
} from '@angular/core';
import { ChattingService } from '@shared/services/common/chatting.service';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { CHAT_INSTANCE } from '@shared/utils/constant';

@Component({
  selector: 'app-chat-index',
  templateUrl: './chat-index.component.html',
  styleUrls: ['./chat-index.component.scss'],
})
export class ChatIndexComponent implements OnDestroy {
  constructor(
    public authStore: AuthStore,
    private chattingService: ChattingService
  ) {
    this.chattingService.isHideChatPopup = true;
  }

  ngOnDestroy(): void {
    this.chattingService.isHideChatPopup = false;
  }
}
