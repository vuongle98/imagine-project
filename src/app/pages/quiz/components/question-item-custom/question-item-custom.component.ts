import { Component, Input } from '@angular/core';
import { Question, QuestionType } from 'src/app/shared/models/quiz';

@Component({
  selector: 'app-question-item-custom',
  templateUrl: './question-item-custom.component.html',
  styleUrls: ['./question-item-custom.component.scss']
})
export class QuestionItemCustomComponent {
  QuestionType = QuestionType;

  @Input() question!: Question;
  @Input() index!: number;

  ngOnInit(): void {

  }
}
