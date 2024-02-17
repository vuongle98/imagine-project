import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {
  listQuizCategory,
  listQuestionType,
} from 'src/app/shared/utils/quiz';

@Component({
  selector: 'app-admin-search-question',
  templateUrl: './admin-search-question.component.html',
  styleUrls: ['./admin-search-question.component.scss'],
})
export class AdminSearchQuestionComponent implements OnInit {
  @Output() emitSearch = new EventEmitter();
  @Output() emitCreate = new EventEmitter();

  listQuestionType = listQuestionType;
  listQuizCategory = listQuizCategory;
  searchForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.searchForm = this.fb.group({
      likeQuestion: [''],
      likeAnswer: [''],
      difficultlyLevel: [''],
      category: [''],
      type: [''],
      mark: [false]
    });
  }

  onSubmit() {
    this.emitSearch.emit(this.searchForm.value);
  }
}
