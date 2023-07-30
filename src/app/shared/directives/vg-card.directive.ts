import { Directive, HostBinding, Input } from '@angular/core';

@Directive({
  selector: '[vg-card]',
})
export class VgCardDirective {
  @Input() @HostBinding('class.card-play') cardPlay = false;

  @Input() @HostBinding('class.card-link') useLink = false;

  @Input() @HostBinding('class.card-odd') odd = false;

  @Input() @HostBinding('class.card-third') third = false;

  constructor() {}
}
