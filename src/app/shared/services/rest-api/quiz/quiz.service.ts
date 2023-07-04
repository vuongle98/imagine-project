import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { CheckAnswer, Quiz } from 'src/app/shared/models/quiz';
import { Pageable } from 'src/app/shared/models/utils';

@Injectable({
  providedIn: 'root',
})
export class QuizService extends AbstractService {
  apiEndpoint = {
    quiz: 'quiz',
    quizWithId: 'quiz/{id}',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  findQuizs(): Observable<Quiz[]> {
    return this.get<Pageable<Quiz[]>>(this.apiEndpoint.quiz, {}).pipe(
      map((res) => res.content),
      shareReplay()
    );
  }

  getQuiz(id: string): Observable<Quiz> {
    return this.get<Quiz>(this.apiEndpoint.quizWithId, { pathParams: { id } });
  }

  checkAnswer(id: string, userAnswer: CheckAnswer[]) {
    return this.post(this.apiEndpoint.quizWithId +"/answer", {
      pathParams: { id },
      requestBody: { data: userAnswer, type: 'application/json' },
    });
  }
}
