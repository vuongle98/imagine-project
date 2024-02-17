import { AfterContentInit, AfterViewInit, Directive, ElementRef, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';

@Directive({
  selector: '[focusInput]'
})
export class FocusInputDirective implements OnInit {

  @ViewChild(HTMLInputElement) input!: ElementRef;

  constructor(private el: ElementRef) {
  }

  ngOnInit() {
    let childs = this.el.nativeElement.childNodes;
    for (const child of childs) {
      if (child instanceof HTMLInputElement) {
        child.focus();
      }
    }
  }
}
