import { Component, Input, OnInit } from '@angular/core';
import { Answer, Question, QuestionType } from 'src/app/shared/models/quiz';

@Component({
  selector: 'app-question-item',
  templateUrl: './question-item.component.html',
  styleUrls: ['./question-item.component.scss'],
})
export class QuestionItemComponent implements OnInit {
  QuestionType = QuestionType;

  @Input() question!: Question;
  @Input() index!: number;

  ngOnInit(): void {

  }
}
