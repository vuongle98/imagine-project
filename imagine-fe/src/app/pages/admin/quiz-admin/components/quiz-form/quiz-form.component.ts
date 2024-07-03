import { Component, Inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { Question, QuizCategory, QuizLevel } from '@shared/models/quiz';
import { DIALOG_DATA } from '@shared/modules/dialog/constants';
import { DialogRef } from '@shared/modules/dialog/dialog-ref';
import { QuestionService } from '@shared/services/rest-api/quiz/question.service';
import { listQuizCategory, listQuizLevel } from '@shared/utils/quiz';
import { BehaviorSubject, debounceTime, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-quiz-form',
  templateUrl: './quiz-form.component.html',
  styleUrls: ['./quiz-form.component.scss'],
})
export class QuizFormComponent implements OnInit {
  createQuizForm!: FormGroup;

  listQuizCategory: any[] = [];
  listQuizLevel: any[] = [];

  questionList: Question[] = [];
  currentQuestionPage = 1;
  currentSearchQuestionKeyword = '';
  searchQuestionChange$ = new BehaviorSubject('');

  constructor(
    private fb: FormBuilder,
    private questionService: QuestionService,
    @Inject(DIALOG_DATA) private data: Question,
    private dialogRef: DialogRef
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.onSearchQuestion('');

    if (this.data) {
      this.createQuizForm.patchValue(this.data);
    }

    this.listQuizCategory = listQuizCategory;
    this.listQuizLevel = listQuizLevel;
  }

  initForm() {
    this.createQuizForm = this.fb.group({
      title: [''],
      description: [''],
      fileId: [''],
      level: [QuizLevel[QuizLevel.EASY]],
      category: [QuizCategory[QuizCategory.GENERAL]],
      published: [false],
      mark: [false],
      addQuestionIds: [[]],
    });
  }

  onSearchQuestion(value: string) {
    this.currentSearchQuestionKeyword = value;

    this.searchQuestionChange$
      .asObservable()
      .pipe(
        debounceTime(300),
        switchMap(() =>
          this.questionService.adminFindQuestions({ likeTitle: value })
        ),
        tap((res) => {
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

  onSubmit() {
    this.dialogRef.close({ id: this.data?.id || null, ...this.createQuizForm.value });
  }

  filterOptions(value: any) {
    console.log(value);
  }

  onScrollToEnd() {
    // append new data to currnet list
    console.log('end');
  }

  onFileSelected(event: any) {
    console.log(event);
  }

  // patchQuestion(questions: Question[]) {
  //   this.createQuizForm.patchValue({
  //     addQuestionIds: questions.map((q) => q.id),
  //   });

  //   console.log(this.createQuizForm.value);
  // }
}
