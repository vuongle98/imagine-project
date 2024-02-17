import { Directive, HostBinding, Input } from '@angular/core';

@Directive({
  selector: '[answer-correct]',
})
export class AnswerCorrectDirective {
  @Input('is-correct') @HostBinding('class.answer-correct') isCorrect = false;
  @Input('is-incorrect') @HostBinding('class.answer-incorrect') isIncorrect = false;
  constructor() {}
}
