import {
  Component, OnInit
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  Observable, tap
} from 'rxjs';
import { CheckAnswer, Question, Quiz } from 'src/app/shared/models/quiz';
import { QuizService } from 'src/app/shared/services/rest-api/quiz/quiz.service';
import { NzModalRef } from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';

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

  confirmModal?: NzModalRef;

  constructor(
    private route: ActivatedRoute,
    private quizService: QuizService,
    private nzMessageService: NzMessageService
  ) {}

  ngOnInit(): void {
    this.quizId = this.route.snapshot.paramMap.get('id') as string;
    // this.route.paramMap.pipe(tap((param) => param.get('id')));

    this.quiz$ = this.quizService.getQuiz(this.quizId);
  }

  next() {
    this.displayItemIndex += 1;
  }

  pre() {
    this.displayItemIndex -= 1;
  }

  done(questions: Question[]) {
    console.log(questions);

    this.commit(questions).subscribe();
  }

  commit(questions: Question[]): Observable<any> {
    const body: CheckAnswer[] = questions.map((question) => {
      const answerIds = !question.checkValue ? [] : [question.checkValue];
      return { questionId: question.id, answerIds };
    });

    return this.quizService.checkAnswer(this.quizId, body).pipe(
      tap((res) => {
        this.nzMessageService.info(
          'Số câu đúng: ' + res.correctAnswers + '/' + res.totalAnswers
        );
        this.isPlaying = false;
      })
    );
  }

  cancel() {}

  timeOverChange(isOver: boolean) {
    isOver && this.next();
  }

  viewAnswer() {

  }
}
