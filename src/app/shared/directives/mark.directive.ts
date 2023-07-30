import { Directive, HostBinding, Input } from '@angular/core';

@Directive({
  selector: '[row-mark]'
})
export class MarkDirective {

  @Input('is-mark') isMark = false;

  constructor() { }

  @HostBinding('class.row-mark')
  get markClass() {
    return this.isMark;
  }

}
