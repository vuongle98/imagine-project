import { SelectionModel } from '@angular/cdk/collections';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Question } from '@shared/models/quiz';
import { DataType } from '@shared/modules/table/table.component';

@Component({
  selector: 'app-question-select',
  templateUrl: './question-select.component.html',
  styleUrls: ['./question-select.component.scss'],
})
export class QuestionSelectComponent implements OnInit {
  @Input() listQuestion: Question[] = [];
  @Input() selectedQuestions: any[] = [];

  selection = new SelectionModel<Question>(true, []);

  @Output() selectedQuestionsChange = new EventEmitter<Question[]>();

  actions = [
    {
      icon: 'edit',
      title: 'Edit',
      action: (item: Question) => {
        this.toggleSelectedQuestion(item);
      },
      show: (item: Question) => true,
    },
  ];

  DataType = DataType;

  constructor() {}

  ngOnInit(): void {
    if (this.selectedQuestions) {
      console.log(this.selectedQuestions);


      this.selectedQuestions.forEach((item) => {
        this.selection.select(item);
      })
    }
  }

  toggleSelectedQuestion(item: Question) {
    this.selection.toggle(item);

    this.selectedQuestionsChange.emit(this.selection.selected);
  }
}
