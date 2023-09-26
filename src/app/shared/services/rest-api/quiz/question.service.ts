import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, filter, shareReplay, tap } from 'rxjs/operators';
import { Question, QuestionQueryParam } from 'src/app/shared/models/quiz';
import { Pageable } from 'src/app/shared/models/utils';
import { MessageService } from '../../common/message.service';

@Injectable({
  providedIn: 'root',
})
export class QuestionService extends AbstractService {
  apiEndpoint = {
    question: 'api/question',
    questionWithId: 'api/question/{id}',

    adminQuestion: 'api/admin/question',
    adminQuestionWithId: 'api/admin/question/{id}',
  };

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService
  ) {
    super(httpClient);
  }

  /**
   * Retrieves a list of questions based on the provided query parameters.
   *
   * @param {QuestionQueryParam} params - The query parameters for filtering the questions.
   * @return {Observable<Pageable<Question[]>>} An observable that emits a pageable list of questions.
   */
  findQuestions(params: QuestionQueryParam): Observable<Pageable<Question[]>> {
    return this.get<Pageable<Question[]>>(this.apiEndpoint.question, {
      queryParams: params,
    }).pipe(
      filter((res) => !!res),
      shareReplay()
    );
  }

  /**
   * Retrieves a question with the specified ID.
   *
   * @param {string} id - The ID of the question to retrieve.
   * @return {Observable<Question>} An observable that emits the question with the specified ID.
   */
  getQuestion(id: string): Observable<Question> {
    return this.get<Question>(this.apiEndpoint.questionWithId, {
      pathParams: { id },
    });
  }

  // only admin using

  /**
   * Retrieves a pageable list of questions based on the provided parameters.
   *
   * @param {QuestionQueryParam} params - The parameters for filtering the questions.
   * @return {Observable<Pageable<Question[]>>} - An observable that emits a pageable list of questions.
   */
  adminFindQuestions(
    params: QuestionQueryParam
  ): Observable<Pageable<Question[]>> {
    return this.get<Pageable<Question[]>>(this.apiEndpoint.adminQuestion, {
      queryParams: params,
    }).pipe(
      filter((res) => !!res),
      shareReplay()
    );
  }

  /**
   * Retrieves a question with the specified ID.
   *
   * @param {string} id - The ID of the question to retrieve.
   * @return {Observable<Question>} An observable that emits the question with the specified ID.
   */
  adminGetQuestion(id: string): Observable<Question> {
    return this.get<Question>(this.apiEndpoint.adminQuestionWithId, {
      pathParams: { id },
    });
  }

  /**
   * Creates a new question.
   *
   * @param {Question} question - The question to create.
   * @return {Observable<Question>} - An observable that emits the created question.
   */
  adminCreateQuestion(question: Question): Observable<Question> {
    return this.post<Question>(this.apiEndpoint.adminQuestion, {
      requestBody: { data: question, type: 'application/json' },
    }).pipe(
      tap(() => this.messageService.displayInfo('Tạo mới thành công')),
      catchError((err) => this.messageService.displayError(err))
    );
  }

  /**
   * Updates a question with the specified ID.
   *
   * @param {string} id - The ID of the question to update.
   * @param {Question} question - The updated question object.
   * @return {Observable<Question>} An observable that emits the updated question.
   */
  adminUpdateQuestion(id: string, question: Question): Observable<Question> {
    return this.put<Question>(this.apiEndpoint.adminQuestionWithId, {
      pathParams: { id },
      requestBody: { data: question, type: 'application/json' },
    }).pipe(
      tap(() => this.messageService.displayInfo('Cập nhật thành công')),
      catchError((err) => this.messageService.displayError(err))
    );
  }

  adminDeleteQuestion(id: string): Observable<any> {
    return this.delete<Question>(this.apiEndpoint.adminQuestionWithId, {
      pathParams: { id },
    }).pipe(
      tap(() => this.messageService.displayInfo('Xóa thành công')),
      catchError((err) => this.messageService.displayError(err))
    );
  }
}
