import { Component, Inject, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  Answer,
  Question,
  QuestionType,
  QuizCategory,
} from '@shared/models/quiz';
import { DIALOG_DATA } from '@shared/modules/dialog/constants';
import { DialogRef } from '@shared/modules/dialog/dialog-ref';
import { AnswerService } from '@shared/services/rest-api/blog/answer.service';
import {
  listDifficultLevel,
  listQuestionType,
  listQuizCategory,
} from '@shared/utils/quiz';
import { BehaviorSubject, debounceTime, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-question-form',
  templateUrl: './question-form.component.html',
  styleUrls: ['./question-form.component.scss'],
})
export class QuestionFormComponent implements OnInit {
  createQuestionForm!: FormGroup;

  listQuestionType = listQuestionType;
  listQuizCategory = listQuizCategory;
  listdifficultLevel = listDifficultLevel;

  answers: Answer[] = [];

  currentAnswerKeyword = '';

  searchAnswerChange$ = new BehaviorSubject('');

  constructor(
    private fb: FormBuilder,
    @Inject(DIALOG_DATA) private data: Question,
    private dialogRef: DialogRef,
    private answerService: AnswerService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.onSearchAnswer('');

    if (this.data) {
      console.log(this.data);
      this.createQuestionForm.patchValue(this.data);
    }
  }

  initForm() {
    this.createQuestionForm = this.fb.group({
      title: [''],
      codeDescription: [''],
      type: [QuestionType[QuestionType.YES_NO]],
      category: [QuizCategory[QuizCategory.GENERAL]],
      answerIds: [[]],
      mark: [false],
      level: [listDifficultLevel[0]],
      countdown: [30],
      active: [true],
    });
  }

  onSearchAnswer(value: string) {
    this.currentAnswerKeyword = value;

    this.searchAnswerChange$
      .asObservable()
      .pipe(
        debounceTime(300),
        switchMap(() =>
          this.answerService.adminSearchAnswer({ likeAnswer: value })
        ),
        tap((res) => {
          this.answers = res.content;
        })
      )
      .subscribe();
  }

  onScrollToEnd() {}

  onSubmit() {
    this.dialogRef.close({
      id: this.data?.id || null,
      ...this.createQuestionForm.value,
    });
  }
}
