import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import {
  LoginPayload,
  RegisterPayload,
  TokenResponse,
} from 'src/app/shared/models/user';
import { Observable } from 'rxjs';
import { ChatMessage, ChatMessageQueryParam } from '@shared/models/chat';
import { Pageable } from '@shared/models/utils';

@Injectable({
  providedIn: 'root',
})
export class MessageService extends AbstractService {
  apiEndpoint = {
    message: 'api/chat-message',
    messageWithId: 'api/chat-message/{id}',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  findMessages(
    param: ChatMessageQueryParam
  ): Observable<Pageable<ChatMessage>> {
    return this.get(this.apiEndpoint.message, { queryParams: param });
  }
}
