import {
  Component,
  ComponentRef,
  OnInit,
  Type,
  ViewChild,
  ViewContainerRef,
} from '@angular/core';
import { Modal } from '@shared/models/modal.model';
import { StickyButtonChatComponent } from '../chat/sticky-button-chat/sticky-button-chat.component';
import { ChattingService } from '@shared/services/common/chatting.service';
import { Observable, map, tap } from 'rxjs';

@Component({
  selector: 'app-chatting-container',
  template: ` <ng-template #container></ng-template> `,
})
export class ChattingContainerComponent implements OnInit {
  @ViewChild('container', { read: ViewContainerRef })
  private container!: ViewContainerRef;

  componentRef?: ComponentRef<StickyButtonChatComponent>;

  constructor(private chattingService: ChattingService) {}

  ngOnInit(): void {
    this.chattingService.isHideChatPopup$.subscribe(() => {
      if (this.componentRef) {
        this.componentRef.destroy();
      }
    });
  }

  renderChatButtonComponent(
    initChat?: any
  ): ComponentRef<StickyButtonChatComponent> | undefined {
    if (this.container) {
      this.container.clear();

      const instance = this.container.createComponent(
        StickyButtonChatComponent
      );
      instance.instance.isShowChat = true;
      instance.instance.initChat = initChat;

      this.componentRef = instance;

      return instance;
    }

    return undefined;
  }
}
