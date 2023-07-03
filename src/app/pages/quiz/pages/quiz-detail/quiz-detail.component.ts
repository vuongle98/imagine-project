import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, startWith } from 'rxjs';
import { Quiz } from 'src/app/shared/models/quiz';
import { QuizService } from 'src/app/shared/services/rest-api/quiz/quiz.service';

@Component({
  selector: 'app-quiz-detail',
  templateUrl: './quiz-detail.component.html',
  styleUrls: ['./quiz-detail.component.scss'],
})
export class QuizDetailComponent implements OnInit {
  quiz$!: Observable<Quiz>;
  currentStep = 0;

  constructor(
    private route: ActivatedRoute,
    private quizService: QuizService
  ) {}

  ngOnInit(): void {
    const quizId = this.route.snapshot.paramMap.get('id') as string;

    this.quiz$ = this.quizService.getQuiz(quizId);
  }

  onIndexChange(index: number) {
    this.currentStep = index;
  }

  pre() {
    this.currentStep -= 1;
  }

  next() {
    this.currentStep += 1;
  }

  done() {}
}
