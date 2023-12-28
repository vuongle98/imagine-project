import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Answer } from '@shared/models/quiz';
import { DIALOG_DATA } from '@shared/modules/dialog/constants';
import { DialogRef } from '@shared/modules/dialog/dialog-ref';

@Component({
  selector: 'app-answer-form',
  templateUrl: './answer-form.component.html',
  styleUrls: ['./answer-form.component.css'],
})
export class AnswerFormComponent {
  createAnswerForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    @Inject(DIALOG_DATA) private data: Answer,
    private dialogRef: DialogRef
  ) {}

  ngOnInit(): void {
    this.initForm();

    if (this.data) {
      console.log(this.data);
      this.createAnswerForm.patchValue(this.data);
    }
  }

  initForm() {
    this.createAnswerForm = this.fb.group({
      content: [''],
      correct: [false],
    });
  }

  onSubmit() {
    this.dialogRef.close({
      id: this.data?.id,
      ...this.createAnswerForm.value,
    });
  }
}
