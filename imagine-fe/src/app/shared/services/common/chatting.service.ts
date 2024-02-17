import { ApplicationRef, ComponentRef, Injectable } from '@angular/core';
import { ConversationService } from '../rest-api/chat/conversation.service';
import { RxStompService } from '../rx-stomp/rx-stomp.service';
import { BehaviorSubject, map, merge, tap } from 'rxjs';
import { Modal } from '@shared/models/modal.model';
import { ModalRef } from '@shared/models/modal-ref.model';
import { ChattingContainerComponent } from '@shared/modules/modal/chatting-container.component';

@Injectable({
  providedIn: 'root',
})
export class ChattingService {
  private modalContainer!: HTMLElement;
  private componentRef!: ComponentRef<ChattingContainerComponent>;

  private _isHideChatPopup = new BehaviorSubject<boolean>(false);
  isHideChatPopup$ = this._isHideChatPopup.asObservable();

  constructor(
    private conversationService: ConversationService,
    private rxStompService: RxStompService,
    private appRef: ApplicationRef
  ) {}

  set isHideChatPopup(value: boolean) {
    this._isHideChatPopup.next(value);
  }

  openChat(initChat?: any): any {
    this.setupModalContainerDiv();

    if (this.componentRef) {
      this.componentRef.destroy();
    }

    const componentRef = this.appRef.bootstrap(
      ChattingContainerComponent,
      this.modalContainer
    );

    componentRef.instance.renderChatButtonComponent(initChat);

    this.componentRef = componentRef;
  }

  closeChat(): void {
    this._isHideChatPopup.next(true);
  }

  private setupModalContainerDiv(): void {
    this.modalContainer = document.createElement('div');
    document
      .getElementsByTagName('app-root')[0]
      .appendChild(this.modalContainer);
  }

  subcribeTo(destination: string) {
    return this.rxStompService
      .watch(destination)
      .pipe(map((message) => JSON.parse(message.body)));
  }

  subcribeAll(destinations: string[]) {
    const needWatch = [];

    for (const destination of destinations) {
      needWatch.push(this.subcribeTo(destination));
    }

    return merge(...needWatch);
  }

  ngOnDestroy(): void {
    this.rxStompService.deactivate();
  }
}
