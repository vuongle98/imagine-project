import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { AdminSearchQuizComponent } from '../../components/admin-search-quiz/admin-search-quiz.component';
import {
  BehaviorSubject,
  debounceTime,
  filter,
  iif,
  switchMap,
  tap,
} from 'rxjs';
import { QuizService } from 'src/app/shared/services/rest-api/quiz/quiz.service';
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
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzModalService } from 'ng-zorro-antd/modal';
import { QuestionService } from 'src/app/shared/services/rest-api/quiz/question.service';

@Component({
  selector: 'app-list-quiz',
  templateUrl: './list-quiz.component.html',
  styleUrls: ['./list-quiz.component.scss'],
})
export class ListQuizComponent implements OnInit, AfterViewInit {
  @ViewChild(AdminSearchQuizComponent)
  adminSearchQuizComponent!: AdminSearchQuizComponent;

  listQuizCategory = listQuizCategory;
  listQuizLevel = listQuizLevel;

  total = 1;
  currentPage = 0;
  listQuiz: Quiz[] = [];

  isShowCreateModal = false;
  selectedQuiz: Quiz = Object.assign({});

  createQuizForm!: FormGroup;

  isLoading = false;
  questionList: Question[] = [];
  currentQuestionPage = 1;
  currentSearchQuestionKeyword = '';
  searchQuestionChange$ = new BehaviorSubject('');

  constructor(
    public quizAdminDataSource: QuizAdminDataSource,
    private fb: FormBuilder,
    private modal: NzModalService,
    private questionService: QuestionService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.quizAdminDataSource.dataSubject
      .pipe(filter((data) => !!data.content))
      .subscribe((res) => {
        this.listQuiz = res.content;
        this.total = res.totalElements;
      });
  }

  initForm() {
    this.createQuizForm = this.fb.group({
      title: [''],
      description: [''],
      level: [QuizLevel[QuizLevel.EASY]],
      category: [QuizCategory[QuizCategory.GENERAL]],
      published: [false],
      mark: [false],
      listQuestionId: [[]],
    });
  }

  ngAfterViewInit(): void {
    this.adminSearchQuizComponent.emitSearch
      .pipe(
        tap((value) => {
          console.log(value);
          this.quizAdminDataSource.loadData(value);
        })
      )
      .subscribe();
  }

  onSearchQuestion(value: string) {
    this.currentSearchQuestionKeyword = value;
    this.isLoading = true;
    this.searchQuestionChange$
      .asObservable()
      .pipe(
        debounceTime(300),
        switchMap(() =>
          this.questionService.adminFindQuestions({ likeTitle: value })
        ),
        tap((res) => {
          this.isLoading = false;
          this.questionList = res.content;
        })
      )
      .subscribe();
  }

  loadMoreQuestion() {
    this.questionService
      .adminFindQuestions({
        likeTitle: this.currentSearchQuestionKeyword,
        page: this.currentQuestionPage,
      })
      .subscribe((res) => {
        this.questionList = [...this.questionList, ...res.content];
        this.currentQuestionPage += 1;
      });
  }

  openCreate() {
    Object.assign({} as Quiz, this.selectedQuiz);
    this.isShowCreateModal = true;
  }

  onQueryParamsChange(nzQueryParams: NzTableQueryParams) {
    const { pageSize, pageIndex } = nzQueryParams;

    this.currentPage = pageIndex - 1;

    const queryParams: QuizQueryParam = {
      size: pageSize,
      page: pageIndex - 1,
    };

    this.quizAdminDataSource.loadData(queryParams);
  }

  handleOk(): void {
    this.isShowCreateModal = false;

    const data = this.createQuizForm.value;

    iif(
      () => !!this.selectedQuiz.id,
      this.quizAdminDataSource.update(this.selectedQuiz.id, data),
      this.quizAdminDataSource.create(data)
    ).subscribe(() => {
      this.quizAdminDataSource.loadData({
        page: this.currentPage,
        size: 10,
      });
      this.initForm();
    });
  }

  deleteQuiz(quiz: Quiz) {
    this.modal.confirm({
      nzTitle: `Are you sure to delete <b>${quiz.title}</b>`,
      nzContent: '<b><i>After delete, you cannot recover this item</i></b>',
      nzOkText: 'Yes',
      nzOkType: 'primary',
      nzOkDanger: true,
      nzOnOk: () => this.confirmDeleteQuiz(quiz),
      nzCancelText: 'No',
      nzCentered: true,
      nzOnCancel: () => console.log('Cancel'),
    });
  }

  confirmDeleteQuiz(quiz: Quiz) {
    this.quizAdminDataSource
      .delete(quiz.id)
      .pipe(
        tap(() => {
          if (this.total <= this.currentPage * 10 + 1) {
            this.currentPage -= 1;
          }
          this.quizAdminDataSource.loadData({
            page: this.currentPage,
            size: 10,
          });
        })
      )
      .subscribe();
  }

  editQuiz(quiz: Quiz) {
    this.selectedQuiz = quiz;

    this.createQuizForm.patchValue(quiz);

    this.isShowCreateModal = true;
  }

  closeModal(): void {
    this.initForm();
    this.isShowCreateModal = false;
  }
}
