import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map, shareReplay } from 'rxjs/operators';
import {
  CheckAnswer,
  CheckAnswerResponse,
  Question,
  QuestionQueryParam,
  Quiz,
} from 'src/app/shared/models/quiz';
import { BaseQueryParam, Pageable } from 'src/app/shared/models/utils';

@Injectable({
  providedIn: 'root',
})
export class QuestionService extends AbstractService {
  apiEndpoint = {
    question: 'question',
    questionWithId: 'question/{id}',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  findQuestions(params: QuestionQueryParam): Observable<Pageable<Question[]>> {
    return this.get<Pageable<Question[]>>(this.apiEndpoint.question, {
      queryParams: params,
    }).pipe(
      filter((res) => !!res),
      shareReplay()
    );
  }

  getQuestion(id: string): Observable<Question> {
    return this.get<Question>(this.apiEndpoint.questionWithId, {
      pathParams: { id },
    });
  }
}
