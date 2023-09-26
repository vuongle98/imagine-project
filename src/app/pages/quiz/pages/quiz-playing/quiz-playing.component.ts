import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, finalize, tap } from 'rxjs';
import {
  BaseCheckAnswer,
  CheckAnswerResponse,
  Question,
  Quiz,
} from 'src/app/shared/models/quiz';
import { QuizService } from 'src/app/shared/services/rest-api/quiz/quiz.service';
import { NotificationService } from '@shared/services/common/notificaton.service';
import { LoadingService } from '@shared/components/loading/loading.service';

@Component({
  selector: 'app-quiz-playing',
  templateUrl: './quiz-playing.component.html',
  styleUrls: ['./quiz-playing.component.scss'],
})
export class QuizPlayingComponent implements OnInit {
  quiz$!: Observable<Quiz>;

  quizId!: string;
  displayItemIndex = 0;

  isPlaying = false;
  isCheckout = false;
  quizResult = {} as CheckAnswerResponse;

  constructor(
    private route: ActivatedRoute,
    private quizService: QuizService,
    private notificationService: NotificationService,
    private loadingService: LoadingService
  ) {}

  ngOnInit(): void {
    this.quizId = this.route.snapshot.paramMap.get('id') as string;
    // this.route.paramMap.pipe(tap((param) => param.get('id')));

    this.loadingService.loadingOn();
    this.quiz$ = this.quizService
      .getQuiz(this.quizId)
      .pipe(finalize(() => this.loadingService.loadingOff()));
  }

  next() {
    this.displayItemIndex += 1;
  }

  pre() {
    this.displayItemIndex -= 1;
  }

  done(questions: Question[]) {
    this.commit(questions).subscribe();
  }

  commit(questions: Question[]): Observable<any> {
    const body: BaseCheckAnswer[] = questions.map((question) => {
      const answerIds = !question.checkValue ? [] : [question.checkValue];
      return { questionId: question.id, answerIds };
    });

    return this.quizService.checkAnswer(this.quizId, body).pipe(
      tap((res) => {
        this.notificationService.info(
          'Thông tin',
          'Số câu đúng: ' + res.numOfCorrectAnswers + '/' + res.totalAnswers
        );
        this.isPlaying = false;
        this.isCheckout = true;
        this.quizResult = res;
      })
    );
  }

  cancel() {}

  timeOverChange(isOver: boolean) {
    isOver && this.next();
  }

  viewAnswer() {}
}
