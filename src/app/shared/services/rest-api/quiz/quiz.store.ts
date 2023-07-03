import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { Quiz } from 'src/app/shared/models/quiz';
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
    this.findQuizs();
  }

  private findQuizs() {
    const loadQuiz$ = this.quizService
      .findQuizs()
      .pipe(tap((quizs) => this._quizs.next(quizs)));

    this.loadingService.showLoaderUntilCompleted(loadQuiz$).subscribe();
  }

  filter(): Observable<Quiz[]> {
    return this.quizs$;
  }
}
