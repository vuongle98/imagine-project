import { Directive, TemplateRef } from '@angular/core';

@Directive({
  selector: '[tableHeader]',
})
export class HeaderDirective {
  constructor(public template: TemplateRef<any>) {}
}
