import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { listQuizCategory } from 'src/app/shared/utils/quiz';

@Component({
  selector: 'app-admin-search-quiz',
  templateUrl: './admin-search-quiz.component.html',
  styleUrls: ['./admin-search-quiz.component.scss']
})
export class AdminSearchQuizComponent {
  listQuizCategory = listQuizCategory;


  @Output() emitSearch = new EventEmitter();

  searchForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.searchForm = this.fb.group({
      likeTitle: [''],
      likeDescription: [''],
      difficultlyLevel: [''],
      category: [''],
    });
  }

  onSubmit() {
    this.emitSearch.emit(this.searchForm.value);
  }
}
