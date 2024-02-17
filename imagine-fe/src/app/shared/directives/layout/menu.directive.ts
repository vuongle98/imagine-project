import { Directive, ElementRef, HostBinding, Input, Renderer2 } from '@angular/core';

@Directive({
  selector: '[menu]',
})
export class MenuDirective {

  @Input('direction') direction: 'horizontal' | 'vertical' = 'horizontal';

  constructor(private renderer: Renderer2, hostElement: ElementRef) {
    renderer.addClass(hostElement.nativeElement, 'menu');
  }
}
