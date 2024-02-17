import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, debounceTime, map, tap, withLatestFrom } from 'rxjs';
import { Quiz, QuizQueryParam } from 'src/app/shared/models/quiz';
import { QuizService } from './quiz.service';
import { LoadingService } from 'src/app/shared/components/loading/loading.service';

@Injectable({
  providedIn: 'root',
})
export class QuizStore {
  private _quizs = new BehaviorSubject<Quiz[]>([]);
  private _isLastPage = new BehaviorSubject<boolean>(false);

  quizs$: Observable<Quiz[]> = this._quizs.asObservable();
  isLastPage$: Observable<boolean> = this._isLastPage.asObservable();

  constructor(
    private quizService: QuizService,
    private loadingService: LoadingService
  ) {
    this.findQuizs({ page: 0, size: 12 });
  }

  findQuizs(params: QuizQueryParam) {
    const loadQuiz$ = this.quizService.findQuizs(params).pipe(
      withLatestFrom(this.quizs$),
      tap(([quizs, oldQuizs]) => {
        if (quizs.last) {
          this._isLastPage.next(true);
        } else {
          this._isLastPage.next(false);
        }
        this._quizs.next([...oldQuizs, ...quizs.content]);
      })
    );

    this.loadingService.showLoaderUntilCompleted(loadQuiz$).subscribe();
  }

  filter(): Observable<Quiz[]> {
    return this.quizs$;
  }
}
