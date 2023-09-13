import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import {
  LoginPayload,
  RegisterPayload,
  TokenResponse,
} from 'src/app/shared/models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ChatService extends AbstractService {
  apiEndpoint = {
    conversations: 'api/conversations',
    conversationWithId: 'api/conversations/{id}',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  findConversations(): Observable<any> {
    return this.get(this.apiEndpoint.conversations, {});
  }

  createConversation(payload: any): Observable<any> {
    return this.post(this.apiEndpoint.conversations, {
      requestBody: { data: payload, type: 'application/json' },
    });
  }

  updateConversation(id: string, payload: any): Observable<any> {
    return this.put(this.apiEndpoint.conversationWithId, {
      pathParams: { id },
      requestBody: { data: payload, type: 'application/json' },
    });
  }
}
