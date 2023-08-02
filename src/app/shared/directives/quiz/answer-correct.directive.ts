import { Directive, HostBinding, Input } from '@angular/core';

@Directive({
  selector: '[answer-correct]',
})
export class AnswerCorrectDirective {
  @Input('is-correct') isCorrect = false;
  constructor() {}

  @HostBinding('class.answer-correct')
  get playClass() {
    return this.isCorrect;
  }
}
