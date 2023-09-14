import { Component, OnInit } from '@angular/core';
import { AuthStore } from './shared/services/rest-api/auth/auth.store';
import { Router } from '@angular/router';
import { fromEvent } from 'rxjs';
import { ChattingService } from '@shared/services/common/chatting.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  windowScrolled = false;

  constructor(public auth: AuthStore, public chattingService: ChattingService) {
    fromEvent(document, 'scroll').subscribe(() => {
      this.windowScrolled = window.scrollY > 100;
    });
  }
  scrollToTop(): void {
    window.scrollTo(0, 0);
  }
}
