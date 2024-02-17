import { Component, Input } from '@angular/core';
import { CheckAnswerResponse, Quiz } from 'src/app/shared/models/quiz';
import { sameStringArray } from 'src/app/shared/utils/common';

@Component({
  selector: 'app-quiz-checkout',
  templateUrl: './quiz-checkout.component.html',
  styleUrls: ['./quiz-checkout.component.scss']
})
export class QuizCheckoutComponent {

  @Input() quizResult!: CheckAnswerResponse;
  @Input() quiz!: Quiz;

  constructor() {

  }

  isUserCorrect(arr1: string[], arr2: string[]) {
    return sameStringArray(arr1, arr2);
  }
}
