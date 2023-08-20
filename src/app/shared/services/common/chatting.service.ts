import { Injectable } from '@angular/core';
import { ChatService } from '../rest-api/chat/chat.service';
import { RxStompService } from '../rx-stomp/rx-stomp.service';
import { Subject, debounceTime, map, merge, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ChattingService {
  constructor(
    private chatService: ChatService,
    private rxStompService: RxStompService
  ) {}

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
