import { Component, Inject } from '@angular/core';
import { DIALOG_DATA } from '@shared/modules/dialog/constants';
import { DialogRef } from '@shared/modules/dialog/dialog-ref';

@Component({
  selector: 'app-test-dialog',
  templateUrl: './test-dialog.component.html',
  styleUrls: ['./test-dialog.component.scss']
})
export class TestDialogComponent {

  constructor(
    @Inject(DIALOG_DATA) public data: any,
    private dialogRef: DialogRef
  ) {
    console.log(data);

  }

  save() {
    this.dialogRef.close({data: 'test'});
  }

}
