import { Directive, HostBinding, Input } from '@angular/core';
import { QuizLevel } from '../../models/quiz';

@Directive({
  selector: '[quiz-level]',
})
export class QuizLevelDirective {
  @Input('quiz-level') quizLevel?: QuizLevel;

  constructor() {}

  @HostBinding('class')
  get quizLevelClass(): string {
    const level = this.quizLevel || 'easy';
    return `quiz-level-${level.toString().toLowerCase()}`;
  }
}
