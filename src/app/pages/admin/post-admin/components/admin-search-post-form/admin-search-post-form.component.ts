import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-admin-search-post-form',
  templateUrl: './admin-search-post-form.component.html',
  styleUrls: ['./admin-search-post-form.component.scss'],
})
export class AdminSearchPostFormComponent {
  @Output() emitSearch = new EventEmitter();
  @Output() emitCreate = new EventEmitter();

  searchForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.searchForm = this.fb.group({
      likeTitle: [''],
      likeDescription: [''],
      categoryId: [''],
      getDeleted: [null],
      featured: [null],
    });
  }

  onSubmit(): void {
    this.emitSearch.emit(this.searchForm.value);
  }
}
