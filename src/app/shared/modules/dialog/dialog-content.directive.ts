import { Directive, Host, HostBinding, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[dialogContent]'
})
export class DialogContentDirective {

  @HostBinding('class') className = 'dialog-content';

  constructor(
    public readonly viewContainerRef: ViewContainerRef
  ) { }

}
