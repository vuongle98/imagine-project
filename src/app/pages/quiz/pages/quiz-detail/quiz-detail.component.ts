import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, startWith } from 'rxjs';
import { CheckAnswer, Question, Quiz } from 'src/app/shared/models/quiz';
import { QuizService } from 'src/app/shared/services/rest-api/quiz/quiz.service';
import { QuestionItemComponent } from '../../components/question-item/question-item.component';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-quiz-detail',
  templateUrl: './quiz-detail.component.html',
  styleUrls: ['./quiz-detail.component.scss'],
})
export class QuizDetailComponent implements OnInit {
  quiz$!: Observable<Quiz>;
  currentStep = 0;
  effect = 'scrollx';
  quizId!: string;
  displayItemIndex = 0;

  confirmModal?: NzModalRef;

  constructor(
    private route: ActivatedRoute,
    private quizService: QuizService,
    private modal: NzModalService
  ) {}

  ngOnInit(): void {
    this.quizId = this.route.snapshot.paramMap.get('id') as string;

    this.quiz$ = this.quizService.getQuiz(this.quizId);
  }

  next() {
    this.displayItemIndex += 1;
  }

  pre() {
    this.displayItemIndex -= 1;
  }

  done(questions: Question[]) {
    questions.filter((item) => !item.checkValue).length > 0 &&
      this.showConfirm(questions);
  }

  commit(questions: Question[]): Observable<any> {
    const body: CheckAnswer[] = questions.map((question) => {
      const answerIds = !question.checkValue ? [] : [question.checkValue];
      return { questionId: question.id, answerIds };
    });

    return this.quizService.checkAnswer(this.quizId, body);
  }

  showConfirm(questions: Question[]): void {
    this.confirmModal = this.modal.confirm({
      nzTitle: 'Xác nhận nộp bài làm',
      nzContent: 'Bài làm chưa hoàn thành, Xác nhận nộp sẽ không thể làm lại được nữa',
      nzOnOk: () => this.commit(questions).subscribe(),
    });
  }
}
