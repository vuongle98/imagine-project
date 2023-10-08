import { Directive, HostBinding, Input } from '@angular/core';

@Directive({
  selector: '[row-mark]',
})
export class MarkDirective {
  @HostBinding('class.row-mark')
  @Input('row-mark')
  rowMark = false;

  constructor() {}
}
