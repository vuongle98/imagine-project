import { Component, ViewChild, ViewContainerRef } from '@angular/core';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';

@Component({
  selector: 'app-chat-index',
  templateUrl: './chat-index.component.html',
  styleUrls: ['./chat-index.component.scss'],
})
export class ChatIndexComponent {


  constructor(public authStore: AuthStore) {

  }

}
