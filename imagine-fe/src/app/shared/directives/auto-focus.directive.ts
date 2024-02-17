import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[autofocus]'
})
export class AutofocusDirective {
  constructor(private el: ElementRef) {
  }

  ngOnInit() {
    const input = this.el.nativeElement as HTMLInputElement;
    input.focus();
  }
}
