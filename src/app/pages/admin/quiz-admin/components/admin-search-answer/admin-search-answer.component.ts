import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-admin-search-answer',
  templateUrl: './admin-search-answer.component.html',
  styleUrls: ['./admin-search-answer.component.css'],
})
export class AdminSearchAnswerComponent {
  @Output() emitSearch = new EventEmitter();
  @Output() emitCreate = new EventEmitter();

  searchForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.searchForm = this.fb.group({
      likeContent: [''],
      correct: [],
    });
  }

  onSubmit() {
    this.emitSearch.emit(this.searchForm.value);
  }
}
