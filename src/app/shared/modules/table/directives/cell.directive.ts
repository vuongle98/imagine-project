import { Directive, Input, TemplateRef } from '@angular/core';

@Directive({
  selector: '[tableCell]'
})
export class CellDirective {

  constructor(
    public template: TemplateRef<any>
  ) { }

}
