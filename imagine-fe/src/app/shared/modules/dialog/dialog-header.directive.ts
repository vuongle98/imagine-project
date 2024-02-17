import { Directive, HostBinding } from '@angular/core';

@Directive({
  selector: '[dialogHeader]'
})
export class DialogHeaderDirective {

  @HostBinding('class') className = 'dialog-header';

  constructor() { }

}
