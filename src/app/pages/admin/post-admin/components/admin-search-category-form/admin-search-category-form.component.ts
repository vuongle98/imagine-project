import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-admin-search-category-form',
  templateUrl: './admin-search-category-form.component.html',
  styleUrls: ['./admin-search-category-form.component.scss'],
})
export class AdminSearchCategoryFormComponent {
  @Output() emitCreate = new EventEmitter();

  @Output() emitSearch = new EventEmitter();

  searchForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.searchForm = this.fb.group({
      likeName: [''],
      getDeleted: [null],
    });
  }

  onSubmit(): void {
    this.emitSearch.emit(this.searchForm.value);
  }
}
