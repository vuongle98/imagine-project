import { Directive, Input, TemplateRef } from '@angular/core';

@Directive({
  selector: '[side]',
})
export class SideDirective {

  @Input() side = 'left';

  constructor(public template: TemplateRef<any>) {}
}
