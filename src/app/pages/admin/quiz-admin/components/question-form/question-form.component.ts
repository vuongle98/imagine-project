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
import {
  listDifficultLevel,
  listQuestionType,
  listQuizCategory,
} from '@shared/utils/quiz';

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

  /**
   * Returns the 'answers' property of the 'createQuestionForm' as a FormArray of FormGroups.
   *
   * @return {FormArray<FormGroup>} - The 'answers' property as a FormArray of FormGroups.
   */
  get answers() {
    return this.createQuestionForm.get('answers') as FormArray<FormGroup>;
  }

  constructor(
    private fb: FormBuilder,
    @Inject(DIALOG_DATA) private data: Question,
    private dialogRef: DialogRef
  ) {}

  ngOnInit(): void {
    this.initForm();

    if (this.data) {
      console.log(this.data);
      this.createQuestionForm.patchValue(this.data);

      const { answers } = this.data;

      answers.forEach((answer) => {
        this.addAnswer(answer);
      });
    } else {
      this.addAnswer();
    }
  }

  initForm() {
    this.createQuestionForm = this.fb.group({
      title: [''],
      codeDescription: [''],
      type: [QuestionType[QuestionType.YES_NO]],
      category: [QuizCategory[QuizCategory.GENERAL]],
      answers: this.fb.array([]),
      mark: [false],
      difficultlyLevel: [listDifficultLevel[0]],
      countDown: [30],
      active: [true],
    });
  }

  addAnswer(answer?: Answer, e?: MouseEvent) {
    if (e) {
      e.preventDefault();
    }

    this.answers.push(
      new FormGroup({
        answer: new FormControl(answer?.answer || '', Validators.required),
        correct: new FormControl(answer?.correct || false),
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

  onSubmit() {
    this.dialogRef.close({
      id: this.data.id,
      ...this.createQuestionForm.value,
    });
  }
}
