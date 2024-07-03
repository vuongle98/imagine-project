import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Category } from '@shared/models/blog';
import { DIALOG_DATA } from '@shared/modules/dialog/constants';
import { DialogRef } from '@shared/modules/dialog/dialog-ref';

@Component({
  selector: 'app-admin-create-category-form',
  templateUrl: './admin-create-category-form.component.html',
  styleUrls: ['./admin-create-category-form.component.scss'],
})
export class AdminCreateCategoryFormComponent {
  createCategoryForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: DialogRef,
    @Inject(DIALOG_DATA) public data: Category
  ) {}

  ngOnInit(): void {
    this.createCategoryForm = this.fb.group({
      name: [''],
    });

    if (this.data) {
      this.createCategoryForm.patchValue(this.data);
    }
  }

  onSubmit() {
    this.dialogRef.close({
      id: this.data?.id || null,
      ...this.createCategoryForm.value,
    });
  }
}
