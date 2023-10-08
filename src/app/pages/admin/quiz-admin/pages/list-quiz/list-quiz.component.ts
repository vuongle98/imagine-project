import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { AdminSearchQuizComponent } from '../../components/admin-search-quiz/admin-search-quiz.component';
import {
  BehaviorSubject,
  catchError,
  concatMap,
  debounceTime,
  filter,
  finalize,
  iif,
  of,
  switchMap,
  tap,
} from 'rxjs';
import { QuizAdminDataSource } from '../../services/quiz-admin.datasource';
import { FormBuilder, FormGroup } from '@angular/forms';
import {
  Question,
  Quiz,
  QuizCategory,
  QuizLevel,
  QuizQueryParam,
} from 'src/app/shared/models/quiz';
import { listQuizCategory, listQuizLevel } from 'src/app/shared/utils/quiz';
import { QuestionService } from 'src/app/shared/services/rest-api/quiz/question.service';
import { DialogService } from '@shared/modules/dialog/dialog.service';
import { QuizFormComponent } from '../../components/quiz-form/quiz-form.component';
import { BaseCrudComponent } from '@shared/components/base-crud/base-crud.component';
import { NotificationService } from '@shared/services/common/notificaton.service';

@Component({
  selector: 'app-list-quiz',
  templateUrl: './list-quiz.component.html',
  styleUrls: ['./list-quiz.component.scss'],
})
export class ListQuizComponent
  extends BaseCrudComponent<QuizAdminDataSource>
  implements OnInit, AfterViewInit
{
  @ViewChild(AdminSearchQuizComponent)
  adminSearchQuizComponent!: AdminSearchQuizComponent;

  totalRows = 1;
  currentPage = 0;
  listQuiz: Quiz[] = [];

  selectedQuiz: Quiz = Object.assign({});

  constructor(
    public quizAdminDataSource: QuizAdminDataSource,
    private fb: FormBuilder,
    private questionService: QuestionService,
    protected override dialogService: DialogService,
    protected override notificationService: NotificationService
  ) {
    super(dialogService, quizAdminDataSource, notificationService);
  }

  ngOnInit(): void {
    this.quizAdminDataSource.loadData({
      page: this.currentPage,
      size: 10,
    });
    this.quizAdminDataSource.dataSubject
      .pipe(filter((data) => !!data.content))
      .subscribe((res) => {
        this.listQuiz = res.content;
        this.totalRows = res.totalElements;
      });
  }

  ngAfterViewInit(): void {
    this.adminSearchQuizComponent.emitSearch
      .pipe(
        tap((value) => {
          this.currentPage = 0;
          this.quizAdminDataSource.loadData({
            ...value,
            page: this.currentPage,
          });
        })
      )
      .subscribe();

    this.adminSearchQuizComponent.emitCreate
      .pipe(
        switchMap(() =>
          this.openCreate(QuizFormComponent, {
            header: 'Create quiz',
          })
        ),
        switchMap((formValue) => this.handleCreateOrUpdate(formValue))
      )
      .subscribe();
  }

  deleteQuiz(quiz: Quiz) {
    this.openDelete(
      'Confirm delete quiz?',
      'Are you sure you want to delete this quiz?',
      quiz
    ).subscribe();
  }

  openEdit(quiz: Quiz) {
    this.selectedQuiz = quiz;

    this.openUpdate(QuizFormComponent, {
      header: 'Update quiz',
      data: this.selectedQuiz,
    })
      .pipe(concatMap((formValue) => this.handleCreateOrUpdate(formValue)))
      .subscribe();
  }
}
