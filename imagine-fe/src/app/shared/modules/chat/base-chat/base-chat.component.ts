import { Component, EventEmitter, Input, Output, TemplateRef } from '@angular/core';

@Component({
  selector: 'app-base-chat',
  templateUrl: './base-chat.component.html',
  styleUrls: ['./base-chat.component.scss']
})
export class BaseChatComponent {

  @Input()
  rightAction!: TemplateRef<any>;

  @Input() currentChatInfo?: any;
}
