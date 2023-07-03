import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Quiz } from 'src/app/shared/models/quiz';
import { QuizService } from 'src/app/shared/services/rest-api/quiz/quiz.service';
import { QuizStore } from 'src/app/shared/services/rest-api/quiz/quiz.store';

@Component({
  selector: 'app-quiz-index',
  templateUrl: './quiz-index.component.html',
  styleUrls: ['./quiz-index.component.scss']
})
export class QuizIndexComponent {

  quiz$!: Observable<Quiz[]>;

  constructor(
    private quizStore: QuizStore,
    private router: Router
  ) {
    this.reloadQuizs();
  }

  navToDetail(id: string){
    this.router.navigate(["/quiz/" +id]);
  }

  reloadQuizs() {
    this.quiz$ = this.quizStore.filter();
  }

}
