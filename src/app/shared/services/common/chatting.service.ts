import { Injectable } from '@angular/core';
import { ChatService } from '../rest-api/chat/chat.service';
import { RxStompService } from '../rx-stomp/rx-stomp.service';
import { BehaviorSubject, Subject, debounceTime, map, merge, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ChattingService {

  private _isHideChatPopup = new BehaviorSubject<boolean>(false);
  isHideChatPopup$ = this._isHideChatPopup.asObservable();

  constructor(
    private chatService: ChatService,
    private rxStompService: RxStompService
  ) {}

  set isHideChatPopup(value: boolean) {
    this._isHideChatPopup.next(value);
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
