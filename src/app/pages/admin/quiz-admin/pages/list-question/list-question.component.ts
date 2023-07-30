import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { QuestionAdminDataSource } from '../../services/quesiton-admin.datasource';
import {
  Question,
  QuizCategory,
  QuestionQueryParam,
  QuestionType,
} from 'src/app/shared/models/quiz';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  listDifficultLevel,
  listQuizCategory,
  listQuestionType,
} from '../../../../../shared/utils/quiz';
import { QuestionService } from 'src/app/shared/services/rest-api/quiz/question.service';
import { filter, iif, tap } from 'rxjs';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { AdminSearchQuestionComponent } from '../../components/admin-search-question/admin-search-question.component';
import { NzModalService } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-list-question',
  templateUrl: './list-question.component.html',
  styleUrls: ['./list-question.component.scss'],
})
export class ListQuestionComponent implements OnInit, AfterViewInit {
  @ViewChild(AdminSearchQuestionComponent)
  adminSearchQuestionComponent!: AdminSearchQuestionComponent;

  listQuestionType = listQuestionType;
  listQuizCategory = listQuizCategory;
  listdifficultLevel = listDifficultLevel;

  total = 1;
  currentPage = 0;
  listQuestion: Question[] = [];

  isShowCreateModal = false;
  selectedQuestion: Question = Object.assign({});

  createQuestionForm!: FormGroup;

  /**
   * Returns the 'answers' property of the 'createQuestionForm' as a FormArray of FormGroups.
   *
   * @return {FormArray<FormGroup>} - The 'answers' property as a FormArray of FormGroups.
   */
  get answers(): FormArray<FormGroup> {
    return this.createQuestionForm.get('answers') as FormArray<FormGroup>;
  }

  constructor(
    public questionAdminDatasource: QuestionAdminDataSource,
    private fb: FormBuilder,
    private questionService: QuestionService,
    private modal: NzModalService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.questionAdminDatasource.dataSubject
      .pipe(filter((data) => !!data.content))
      .subscribe((res) => {
        this.listQuestion = res.content;
        this.total = res.totalElements;
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
  }

  /**
   * Resets the answers.
   *
   * @return {void} No return value.
   */
  resetAnswers(): void {
    this.answers.clear();
  }

  /**
   * Initializes the form.
   *
   * @returns {void} - Does not return a value.
   */
  initForm(): void {
    this.createQuestionForm = this.fb.group({
      title: [''],
      type: [QuestionType[QuestionType.YES_NO]],
      category: [QuizCategory[QuizCategory.GENERAL]],
      answers: this.fb.array([]),
      mark: [false],
      difficultlyLevel: [1],
      countDown: [30],
      active: [true],
    });
  }

  onQueryParamsChange(nzParams: NzTableQueryParams): void {
    const { pageSize, pageIndex } = nzParams;

    this.currentPage = pageIndex - 1;

    const queryParams: QuestionQueryParam = {
      size: pageSize,
      page: pageIndex - 1,
    };

    this.questionAdminDatasource.loadData(queryParams);
  }

  openCreate() {
    Object.assign({} as Question, this.selectedQuestion);
    this.isShowCreateModal = true;
  }

  handleOk(): void {
    this.isShowCreateModal = false;

    const data = this.createQuestionForm.value;

    iif(
      () => !!this.selectedQuestion.id,
      this.questionService.adminUpdateQuestion(this.selectedQuestion.id, data),
      this.questionService.adminCreateQuestion(data)
    ).subscribe(() => {
      this.questionAdminDatasource.loadData({
        page: this.currentPage,
        size: 10,
      });
      this.resetAnswers();
      this.initForm();
    });
  }

  closeModal(): void {
    this.resetAnswers();
    this.initForm();
    this.isShowCreateModal = false;
  }

  addAnswer(e?: MouseEvent) {
    if (e) {
      e.preventDefault();
    }

    this.answers.push(
      new FormGroup({
        answer: new FormControl('', Validators.required),
        correct: new FormControl(false),
      })
    );
  }

  removeAnswer(control: FormGroup, e: MouseEvent) {
    e.preventDefault();

    if (this.answers.controls.length > 1) {
      const index = this.answers.controls.indexOf(control);
      this.answers.removeAt(index);
    }
  }

  editQuestion(question: Question) {
    this.selectedQuestion = question;

    question.answers.map((answer) => {
      this.answers.push(
        new FormGroup({
          answer: new FormControl(answer.answer),
          correct: new FormControl(answer.correct),
        })
      );
    });

    this.createQuestionForm.patchValue(question);

    this.isShowCreateModal = true;
  }

  deleteQuestion(question: Question) {
    this.modal.confirm({
      nzTitle: `Are you sure to delete <b>${question.title}</b>`,
      nzContent: '<b><i>After delete, you cannot recover this item</i></b>',
      nzOkText: 'Yes',
      nzOkType: 'primary',
      nzOkDanger: true,
      nzOnOk: () => this.confirmDeleteQuestion(question),
      nzCancelText: 'No',
      nzCentered: true,
      nzOnCancel: () => console.log('Cancel'),
    });
  }

  confirmDeleteQuestion(question: Question) {
    this.questionService
      .adminDeleteQuestion(question.id)
      .pipe(
        tap(() => {
          if (this.total <= this.currentPage * 10 + 1) {
            this.currentPage -= 1;
          }

          this.questionAdminDatasource.loadData({
            page: this.currentPage,
            size: 10,
          });
        })
      )
      .subscribe();
  }
}
