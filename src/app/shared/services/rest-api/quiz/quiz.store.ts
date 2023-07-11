import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map, tap, withLatestFrom } from 'rxjs';
import { Quiz, QuizQueryParam } from 'src/app/shared/models/quiz';
import { QuizService } from './quiz.service';
import { LoadingService } from 'src/app/shared/components/loading/loading.service';

@Injectable({
  providedIn: 'root',
})
export class QuizStore {
  private _quizs = new BehaviorSubject<Quiz[]>([]);

  quizs$: Observable<Quiz[]> = this._quizs.asObservable();

  constructor(
    private quizService: QuizService,
    private loadingService: LoadingService
  ) {
    this.findQuizs({ page: 0, size: 10 });
  }

  findQuizs(params: QuizQueryParam) {
    const loadQuiz$ = this.quizService.findQuizs(params).pipe(
      withLatestFrom(this.quizs$),
      tap(([quizs, oldQuizs]) => this._quizs.next([...oldQuizs, ...quizs]))
    );

    this.loadingService.showLoaderUntilCompleted(loadQuiz$).subscribe();
  }

  filter(): Observable<Quiz[]> {
    return this.quizs$;
  }
}
