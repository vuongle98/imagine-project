import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { BaseCrudComponent } from '@shared/components/base-crud/base-crud.component';
import { AnswerAdminDataSource } from '../../services/answer-admin.datasource';
import { AdminSearchAnswerComponent } from '../../components/admin-search-answer/admin-search-answer.component';
import { Answer } from '@shared/models/quiz';
import { FormBuilder } from '@angular/forms';
import { AnswerService } from '@shared/services/rest-api/blog/answer.service';
import { DialogService } from '@shared/modules/dialog/dialog.service';
import { NotificationService } from '@shared/services/common/notificaton.service';
import { Observable, filter, iif, of, switchMap, tap } from 'rxjs';
import { AnswerFormComponent } from '../../components/answer-form/answer-form.component';

@Component({
  selector: 'app-list-admin-answer',
  templateUrl: './list-admin-answer.component.html',
  styleUrls: ['./list-admin-answer.component.css'],
})
export class ListAdminAnswerComponent
  extends BaseCrudComponent<AnswerAdminDataSource>
  implements OnInit, AfterViewInit
{
  @ViewChild(AdminSearchAnswerComponent)
  adminSearchAnswerComponent!: AdminSearchAnswerComponent;

  totalRows = 1;
  currentPage = 0;
  listAnswer: Answer[] = [];

  selectedQuestion: Answer = Object.assign({});

  constructor(
    public answerAdminDataSource: AnswerAdminDataSource,
    private fb: FormBuilder,
    private answerService: AnswerService,
    protected override dialogService: DialogService,
    protected override notificationService: NotificationService
  ) {
    super(dialogService, answerAdminDataSource, notificationService);
  }

  ngOnInit(): void {
    this.answerAdminDataSource.loadData({
      page: this.currentPage,
      size: 10,
    });
    this.answerAdminDataSource.dataSubject
      .pipe(filter((data) => !!data.content))
      .subscribe((res) => {
        this.listAnswer = res.content;
        this.totalRows = res.totalElements;
      });
  }

  ngAfterViewInit(): void {
    this.adminSearchAnswerComponent.emitSearch
      .pipe(
        tap((value) => {
          // value = { ...value, page: 0, size: 10 };
          this.answerAdminDataSource.loadData(value);
        })
      )
      .subscribe();

    this.adminSearchAnswerComponent.emitCreate
      .pipe(
        switchMap(() =>
          this.openCreate(AnswerFormComponent, {
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
          this.answerAdminDataSource.loadData({
            page: this.currentPage,
            size: 10,
          });
      });
  }

  handleOk(data: Answer): Observable<Answer | null> {
    if (!data) return of(null);

    return iif(
      () => !!this.selectedQuestion.id,
      this.answerService.adminUpdate(this.selectedQuestion.id, data),
      this.answerService.adminCreate(data)
    );
  }

  editAnswer(answer: Answer) {
    this.selectedQuestion = answer;
    this.openUpdate(AnswerFormComponent, {
      header: 'Update answer',
      data: this.selectedQuestion,
    })
      .pipe(
        tap((v) => console.log(v)),
        switchMap((formValue) => this.handleOk(formValue as Answer))
      )
      .subscribe((result) => {
        !!result &&
          this.answerAdminDataSource.loadData({
            page: this.currentPage,
            size: 10,
          });
      });
  }

  deleteAnswer(answer: Answer) {
    this.openDelete(
      'Confirm delete answer?',
      'Are you sure you want to delete this answer?',
      answer
    ).subscribe();
  }
}
