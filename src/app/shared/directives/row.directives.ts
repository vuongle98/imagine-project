import { Directive, ElementRef } from '@angular/core';
@Directive({
  selector: '[row]',
})
export class RowDirective {
  constructor(private eleRef: ElementRef) {
    eleRef.nativeElement.style.background = 'red';
  }
}
