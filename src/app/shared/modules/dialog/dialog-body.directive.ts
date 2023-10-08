import { Directive, HostBinding } from '@angular/core';

@Directive({
  selector: '[dialogBody]',
})
export class DialogBodyDirective {
  @HostBinding('class') className = 'dialog-body';

  constructor() {}
}
