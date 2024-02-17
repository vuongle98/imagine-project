import { Injectable } from '@angular/core';
import { LoadingService } from '@shared/components/loading/loading.service';
import { ChatMessage, ChatMessageQueryParam } from '@shared/models/chat';
import { ConversationService } from '@shared/services/rest-api/chat/conversation.service';
import { MessageService } from '@shared/services/rest-api/chat/message.service';
import { UserService } from '@shared/services/rest-api/user/user.service';
import { BehaviorSubject, Observable, of, tap } from 'rxjs';
import { BaseDataSource } from 'src/app/shared/datasource/base-datasource';
import { Pageable } from 'src/app/shared/models/utils';

@Injectable({
  providedIn: 'root',
})
export class ChatDataSource extends BaseDataSource<Pageable<ChatMessage>> {
  override dataSubject: BehaviorSubject<Pageable<ChatMessage>> =
    new BehaviorSubject({} as Pageable<ChatMessage>);

  constructor(
    private messageService: MessageService,
    private loadingService: LoadingService
  ) {
    super();
  }

  loadData(params: ChatMessageQueryParam) {
    if (!params?.page || !params?.size) {
      params.page = 0;
      params.size = 20;
    }

    const loadUser$ = this.messageService
      .findMessages(params)
      .pipe(tap((data) => this.dataSubject.next(data)));

    this.loadingService.showLoaderUntilCompleted(loadUser$).subscribe();
  }

  override create(data: any): Observable<any> {
    return of(null);
  }

  override delete(id: string, forever?: boolean | undefined): Observable<any> {
    return of(null);
  }

  override update(id: string, data: any): Observable<any> {
    return of(null);
  }
}
