import { Component, Inject, OnInit } from '@angular/core';
import { DIALOG_DATA } from '@shared/modules/dialog/constants';
import { DialogRef } from '@shared/modules/dialog/dialog-ref';

export type ConfirmData = {
  dialogData: ConfirmDialogData;
  resData: any;
};

export type ConfirmDialogData = {
  description: string;
  icon: string;
};

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss'],
})
export class ConfirmComponent {
  constructor(
    @Inject(DIALOG_DATA) public readonly data: ConfirmData,
    private dialogRef: DialogRef
  ) {}

  confirm() {
    this.dialogRef.close({ isConfirmed: true, data: this.data.resData });
  }
}
