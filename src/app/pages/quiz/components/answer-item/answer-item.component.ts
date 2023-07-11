import { Component, Input } from '@angular/core';
import { Answer, Question } from 'src/app/shared/models/quiz';

@Component({
  selector: 'app-answer-item',
  templateUrl: './answer-item.component.html',
  styleUrls: ['./answer-item.component.scss']
})
export class AnswerItemComponent {

  listAlphabet = ["A", "B", "C", "D", "E", "F"];

  @Input() answer!: Answer;
  @Input() question!: Question;
  @Input() index!: number;
}
