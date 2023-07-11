import { Component, OnInit } from '@angular/core';
import { QuestionAdminDataSource } from '../../services/quesiton-admin.datasource';
import {
  QuestionCategory,
  QuestionQueryParam,
  QuestionType,
} from 'src/app/shared/models/quiz';
import { FormBuilder, FormGroup } from '@angular/forms';
import {
  listQuestionCategory,
  listQuestionType,
} from '../../../../../shared/utils/quiz';

@Component({
  selector: 'app-list-question',
  templateUrl: './list-question.component.html',
  styleUrls: ['./list-question.component.scss'],
})
export class ListQuestionComponent implements OnInit {
  listQuestionType = listQuestionType;
  listQuestionCategory = listQuestionCategory;
  isShowCreateModal = false;

  createQuestionForm!: FormGroup;

  constructor(
    public questionAdminDatasource: QuestionAdminDataSource,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.createQuestionForm = this.fb.group({
      title: [null],
      type: [QuestionType.YES_NO],
      category: [QuestionCategory.GENERAL],
    });
  }

  openCreate() {
    this.isShowCreateModal = true;
  }

  handleOkMiddle(): void {
    console.log('click ok');
    console.log(this.createQuestionForm.value);
    this.isShowCreateModal = false;
  }

  closeModal(): void {
    this.isShowCreateModal = false;
  }
}
