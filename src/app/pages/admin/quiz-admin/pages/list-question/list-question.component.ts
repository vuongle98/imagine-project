import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { QuestionAdminDataSource } from '../../services/quesiton-admin.datasource';
import { Question } from 'src/app/shared/models/quiz';
import { FormBuilder } from '@angular/forms';
import { QuestionService } from 'src/app/shared/services/rest-api/quiz/question.service';
import { Observable, filter, iif, of, switchMap, tap } from 'rxjs';
import { AdminSearchQuestionComponent } from '../../components/admin-search-question/admin-search-question.component';
import { DialogService } from '@shared/modules/dialog/dialog.service';
import { QuestionFormComponent } from '../../components/question-form/question-form.component';
import { BaseCrudComponent } from '@shared/components/base-crud/base-crud.component';
import { NotificationService } from '@shared/services/common/notificaton.service';

@Component({
  selector: 'app-list-question',
  templateUrl: './list-question.component.html',
  styleUrls: ['./list-question.component.scss'],
})
export class ListQuestionComponent
  extends BaseCrudComponent<QuestionAdminDataSource>
  implements OnInit, AfterViewInit
{
  @ViewChild(AdminSearchQuestionComponent)
  adminSearchQuestionComponent!: AdminSearchQuestionComponent;

  totalRows = 1;
  currentPage = 0;
  listQuestion: Question[] = [];

  selectedQuestion: Question = Object.assign({});

  constructor(
    public questionAdminDatasource: QuestionAdminDataSource,
    private fb: FormBuilder,
    private questionService: QuestionService,
    protected override dialogService: DialogService,
    protected override notificationService: NotificationService
  ) {
    super(dialogService, questionAdminDatasource, notificationService);
  }

  ngOnInit(): void {
    this.questionAdminDatasource.loadData({
      page: this.currentPage,
      size: 10,
    });
    this.questionAdminDatasource.dataSubject
      .pipe(filter((data) => !!data.content))
      .subscribe((res) => {
        this.listQuestion = res.content;
        this.totalRows = res.totalElements;
      });
  }

  ngAfterViewInit(): void {
    this.adminSearchQuestionComponent.emitSearch
      .pipe(
        tap((value) => {
          // value = { ...value, page: 0, size: 10 };
          this.questionAdminDatasource.loadData(value);
        })
      )
      .subscribe();

    this.adminSearchQuestionComponent.emitCreate
      .pipe(
        switchMap(() =>
          this.openCreate(QuestionFormComponent, {
            header: 'Create question',
          })
        ),
        switchMap((formValue) => {
          console.log(formValue);
          return this.handleOk(formValue);
        })
      )
      .subscribe((result) => {
        !!result &&
          this.questionAdminDatasource.loadData({
            page: this.currentPage,
            size: 10,
          });
      });
  }

  handleOk(data: Question): Observable<Question | null> {
    if (!data) return of(null);

    return iif(
      () => !!this.selectedQuestion.id,
      this.questionService.adminUpdateQuestion(this.selectedQuestion.id, data),
      this.questionService.adminCreateQuestion(data)
    );
  }

  editQuestion(question: Question) {
    this.selectedQuestion = question;
    this.openUpdate(QuestionFormComponent, {
      header: 'Update question',
      data: this.selectedQuestion,
    })
      .pipe(
        tap((v) => console.log(v)),
        switchMap((formValue) => this.handleOk(formValue as Question))
      )
      .subscribe((result) => {
        !!result &&
          this.questionAdminDatasource.loadData({
            page: this.currentPage,
            size: 10,
          });
      });
  }

  deleteQuestion(question: Question) {
    this.openDelete(
      'Confirm delete question?',
      'Are you sure you want to delete this question?',
      question
    ).subscribe();
  }
}
