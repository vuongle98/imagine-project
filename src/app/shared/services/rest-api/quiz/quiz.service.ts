import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map, shareReplay, tap } from 'rxjs/operators';
import {
  BaseCheckAnswer,
  CheckAnswer,
  CheckAnswerResponse,
  Quiz,
  QuizQueryParam,
} from 'src/app/shared/models/quiz';
import { Pageable } from 'src/app/shared/models/utils';
import { MessageService } from '../../common/message.service';

@Injectable({
  providedIn: 'root',
})
export class QuizService extends AbstractService {
  apiEndpoint = {
    quiz: 'api/quiz',
    quizWithId: 'api/quiz/{id}',

    adminQuiz: 'api/admin/quiz',
    adminQuizWithId: 'api/admin/quiz/{id}',
  };

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService
  ) {
    super(httpClient);
  }

  findQuizs(params: QuizQueryParam): Observable<Pageable<Quiz[]>> {
    return this.get<Pageable<Quiz[]>>(this.apiEndpoint.quiz, {
      queryParams: params,
    }).pipe(shareReplay());
  }

  getQuiz(id: string): Observable<Quiz> {
    return this.get<Quiz>(this.apiEndpoint.quizWithId, { pathParams: { id } });
  }

  checkAnswer(
    id: string,
    userAnswer: BaseCheckAnswer[]
  ): Observable<CheckAnswerResponse> {
    return this.post(this.apiEndpoint.quizWithId + '/answer', {
      pathParams: { id },
      requestBody: { data: userAnswer, type: 'application/json' },
    });
  }

  // only admin using

  /**
   * Retrieves a list of quizzes based on the provided query parameters.
   *
   * @param {QuizQueryParam} params - The query parameters used to filter the quizzes.
   * @return {Observable<Pageable<Quiz[]>>} - An observable emitting a pageable list of quizzes.
   */
  adminFindQuizs(params: QuizQueryParam): Observable<Pageable<Quiz[]>> {
    return this.get<Pageable<Quiz[]>>(this.apiEndpoint.adminQuiz, {
      queryParams: params,
    });
  }

  /**
   * Creates a new quiz as an admin.
   *
   * @param {Quiz} quiz - The quiz object to be created.
   * @return {Observable<Quiz>} - An observable that emits the created quiz.
   */
  adminCreateQuiz(quiz: Quiz): Observable<Quiz> {
    return this.post<Quiz>(this.apiEndpoint.adminQuiz, {
      requestBody: { data: quiz, type: 'application/json' },
    }).pipe(
      tap(() => this.messageService.displayInfo('Tạo mới thành công')),
      catchError((err) => this.messageService.displayError(err))
    );
  }

  /**
   * Updates a quiz for the admin.
   *
   * @param {string} id - The ID of the quiz to be updated.
   * @param {Quiz} quiz - The updated quiz object.
   * @return {Observable<Quiz>} - Returns an observable of the updated quiz.
   */
  adminUpdateQuiz(id: string, quiz: Quiz): Observable<Quiz> {
    return this.put<Quiz>(this.apiEndpoint.adminQuizWithId, {
      pathParams: { id },
      requestBody: { data: quiz, type: 'application/json' },
    }).pipe(
      tap(() => this.messageService.displayInfo('Cập nhật thành công')),
      catchError((err) => this.messageService.displayError(err))
    );
  }

  /**
   * Deletes a quiz as an admin.
   *
   * @param {string} id - The ID of the quiz to be deleted.
   * @return {Observable<any>} An Observable that emits the response from the server.
   */
  adminDeleteQuiz(id: string): Observable<any> {
    return this.delete(this.apiEndpoint.adminQuizWithId, {
      pathParams: { id },
    }).pipe(
      tap(() => this.messageService.displayInfo('Xóa thành công')),
      catchError((err) => this.messageService.displayError(err))
    );
  }
}
