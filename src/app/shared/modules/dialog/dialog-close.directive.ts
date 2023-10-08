import { Directive, HostListener, Input, Optional } from '@angular/core';
import { DialogRef } from './dialog-ref';

@Directive({
  selector: '[dialogClose]',
})
export class DialogCloseDirective<T = any> {
  @Input('dialogClose') dialogResult?: T;
  constructor(@Optional() private dialogRef: DialogRef<T>) {}

  @HostListener('click') onclick(): void {
    if (!this.dialogRef) {
      return;
    }

    this.dialogRef.close(this.dialogResult);
  }
}
