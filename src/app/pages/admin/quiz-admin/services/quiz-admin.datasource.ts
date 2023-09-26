import { Injectable } from '@angular/core';
import { LoadingService } from '@shared/components/loading/loading.service';
import { BehaviorSubject, Observable, finalize, of, tap } from 'rxjs';
import { BaseDataSource } from 'src/app/shared/datasource/base-datasource';
import { Quiz, QuizQueryParam } from 'src/app/shared/models/quiz';
import { Pageable } from 'src/app/shared/models/utils';
import { QuizService } from 'src/app/shared/services/rest-api/quiz/quiz.service';

@Injectable({
  providedIn: 'root',
})
export class QuizAdminDataSource extends BaseDataSource<Pageable<Quiz[]>> {
  override dataSubject: BehaviorSubject<Pageable<Quiz[]>> = new BehaviorSubject(
    {} as Pageable<Quiz[]>
  );

  constructor(
    private quizService: QuizService,
    private loadingService: LoadingService
  ) {
    super();
  }

  loadData(params: QuizQueryParam) {
    if (!params?.page || !params?.size) {
      params.page = 0;
      params.size = 10;
    }

    const loadQuiz$ = this.quizService
      .adminFindQuizs(params)
      .pipe(tap((data) => this.dataSubject.next(data)));

    this.loadingService.showLoaderUntilCompleted(loadQuiz$).subscribe();
  }

  ngOnDestroy(): void {
    this.dataSubject.complete();
  }

  create(quiz: Quiz): Observable<Quiz> {
    return this.quizService.adminCreateQuiz(quiz);
  }

  update(id: string, quiz: Quiz): Observable<Quiz> {
    return this.quizService.adminUpdateQuiz(id, quiz);
  }

  delete(id: string): Observable<void> {
    return this.quizService.adminDeleteQuiz(id);
  }
}
