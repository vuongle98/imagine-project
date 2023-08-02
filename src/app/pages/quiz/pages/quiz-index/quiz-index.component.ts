import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LoadingService } from 'src/app/shared/components/loading/loading.service';
import { Quiz } from 'src/app/shared/models/quiz';
import { QuizStore } from 'src/app/shared/services/rest-api/quiz/quiz.store';

@Component({
  selector: 'app-quiz-index',
  templateUrl: './quiz-index.component.html',
  styleUrls: ['./quiz-index.component.scss'],
})
export class QuizIndexComponent {
  quiz$!: Observable<Quiz[]>;
  currentPage = 0;
  pageSize = 12;

  constructor(
    public quizStore: QuizStore,
    private router: Router,
    public loadingService: LoadingService
  ) {
    this.reloadQuizs();
  }

  navToPlaying(id: string) {
    this.router.navigate(['/quiz/playing' + id]);
  }

  reloadQuizs() {
    this.quiz$ = this.quizStore.filter();
  }

  loadMore() {
    console.log('loading');
    this.currentPage += 1;
    this.quizStore.findQuizs({ page: this.currentPage, size: 12 });
  }
}
