import { Component, Inject, OnInit } from '@angular/core';
import { DIALOG_DATA } from '@shared/modules/dialog/constants';
import { DialogRef } from '@shared/modules/dialog/dialog-ref';

export type ConfirmData = {
  dialogData: ConfirmDialogData;
  resData: any;
  askForce?: boolean;
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
  isForce: boolean = false;

  constructor(
    @Inject(DIALOG_DATA) public readonly data: ConfirmData,
    private dialogRef: DialogRef
  ) {}

  confirm() {
    this.dialogRef.close({
      isConfirmed: true,
      isForce: this.isForce,
      data: this.data.resData,
    });
  }
}
