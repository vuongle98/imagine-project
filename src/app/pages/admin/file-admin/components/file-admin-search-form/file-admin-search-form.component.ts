import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-file-admin-search-form',
  templateUrl: './file-admin-search-form.component.html',
  styleUrls: ['./file-admin-search-form.component.scss'],
})
export class FileAdminSearchFormComponent {
  @Output() emitCreate = new EventEmitter();
  @Output() emitSearch = new EventEmitter();

  searchForm!: FormGroup;

  constructor(private fb: FormBuilder) {
    this.searchForm = this.fb.group({
      type: [''],
      likeName: [''],
    });
  }

  onSubmit() {
    this.emitSearch.emit(this.searchForm.value);
  }
}
