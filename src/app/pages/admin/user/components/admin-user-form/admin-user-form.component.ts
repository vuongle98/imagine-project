import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DIALOG_DATA } from '@shared/modules/dialog/constants';
import { DialogRef } from '@shared/modules/dialog/dialog-ref';

@Component({
  selector: 'app-admin-user-form',
  templateUrl: './admin-user-form.component.html',
  styleUrls: ['./admin-user-form.component.scss'],
})
export class AdminUserFormComponent implements OnInit {
  userForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    @Inject(DIALOG_DATA) private data: any,
    private dialogRef: DialogRef
  ) {}

  ngOnInit(): void {
    this.initForm();

    if (this.data) {
      this.userForm.patchValue(this.data);
    }
  }

  initForm() {
    this.userForm = this.fb.group({
      username: [''],
      fullName: [''],
      email: [''],
      password: [''],
      roles: [''],
    });
  }

  onSubmit() {
    this.dialogRef.close({ id: this.data?.id, ...this.userForm.value });
  }
}
