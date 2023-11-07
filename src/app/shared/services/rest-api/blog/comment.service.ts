import { Injectable } from "@angular/core";
import { AbstractService } from "../abstract-service";
import { HttpClient } from "@angular/common/http";
import { MessageService } from "@shared/services/common/message.service";

@Injectable({
  providedIn: 'root',
})
export class CommentService extends AbstractService {
  apiEndpoint = {
    comment: 'api/comment',
    commentWithId: 'api/comment/{id}',
  };

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService
  ) {
    super(httpClient);
  }



}
