import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-admin-search-user',
  templateUrl: './admin-search-user.component.html',
  styleUrls: ['./admin-search-user.component.scss'],
})
export class AdminSearchUserComponent {
  searchForm!: FormGroup;

  @Output() emitSearch = new EventEmitter();
  @Output() emitCreate = new EventEmitter();

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.searchForm = this.fb.group({
      likeUsername: [''],
      likeEmail: [''],
      likeFullName: [''],
      role: [''],
    });
  }

  onSubmit() {
    this.emitSearch.emit(this.searchForm.value);
  }
}
