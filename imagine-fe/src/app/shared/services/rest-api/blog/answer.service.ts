import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { MessageService } from '@shared/services/common/message.service';
import { Observable, filter, shareReplay } from 'rxjs';
import { Pageable } from '@shared/models/utils';
import { Answer, AnswerFilter } from '@shared/models/quiz';

@Injectable({
  providedIn: 'root',
})
export class AnswerService extends AbstractService {
  apiEndpoint = {
    answer: 'api/admin/answer',
    answerWithId: 'api/admin/answer/{id}',
    adminAnswer: 'api/admin/answer',
    adminAnswerWithId: 'api/admin/answer/{id}',
  };

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService
  ) {
    super(httpClient);
  }

  find(params: AnswerFilter): Observable<Pageable<Answer>> {
    return this.get<Pageable<Answer>>(this.apiEndpoint.answer, {
      queryParams: params,
    }).pipe(
      filter((res) => !!res),
      shareReplay()
    );
  }

  createAnswer(answer: Answer): Observable<Answer> {
    return this.post<Answer>(this.apiEndpoint.answer, {
      requestBody: { type: 'application/json', data: answer },
    });
  }

  updateAnswer(id: string, category: Answer): Observable<Answer> {
    return this.put<Answer>(this.apiEndpoint.answerWithId, {
      pathParams: { id },
      requestBody: { type: 'application/json', data: category },
    });
  }

  deleteAnswer(id: string, force = false): Observable<any> {
    return this.delete(this.apiEndpoint.answerWithId, {
      pathParams: { id },
      queryParams: { force },
    });
  }

  // admin

  adminSearchAnswer(params: AnswerFilter): Observable<Pageable<Answer>> {
    return this.get<Pageable<Answer>>(this.apiEndpoint.adminAnswer, {
      queryParams: params,
    });
  }

  adminCreate(post: Answer): Observable<Answer> {
    return this.post(this.apiEndpoint.adminAnswer, {
      requestBody: { data: post, type: 'application/json' },
    });
  }

  adminUpdate(id: string, post: Answer): Observable<Answer> {
    return this.put<Answer>(this.apiEndpoint.adminAnswerWithId, {
      pathParams: { id },
      requestBody: { data: post, type: 'application/json' },
    });
  }

  adminDeleteAnswer(id: string, force?: boolean | undefined): Observable<any> {
    return this.delete(this.apiEndpoint.adminAnswerWithId, {
      pathParams: { id },
      queryParams: { force },
    });
  }
}
