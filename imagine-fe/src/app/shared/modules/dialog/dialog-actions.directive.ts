import { Directive, HostBinding } from '@angular/core';

@Directive({
  selector: '[dialogActions]',
})
export class DialogActionsDirective {
  @HostBinding('class') className = 'dialog-actions';

  constructor() {}
}
