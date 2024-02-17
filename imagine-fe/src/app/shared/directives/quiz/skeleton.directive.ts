import { Directive, HostBinding, Input } from '@angular/core';
import { QuizLevel } from '../../models/quiz';

@Directive({
  selector: '[quiz-skeleton]',
})
export class SkeletonDirective {
  @Input('quiz-skeleton') @HostBinding('class.quiz-skeleton') skeleton = false;

  constructor() {}
}
