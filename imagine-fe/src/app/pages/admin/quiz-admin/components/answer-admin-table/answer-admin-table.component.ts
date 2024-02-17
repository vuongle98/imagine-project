import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';
import { AnswerAdminDataSource } from '../../services/answer-admin.datasource';
import { Answer } from '@shared/models/quiz';
import { DataType } from '@shared/modules/table/table.component';

@Component({
  selector: 'app-answer-admin-table',
  templateUrl: './answer-admin-table.component.html',
  styleUrls: ['./answer-admin-table.component.css'],
})
export class AnswerAdminTableComponent {
  @Input() answerAdminDataSource!: AnswerAdminDataSource;
  @Input() listAnswer: Answer[] = [];
  @Input() totalRows = 1;
  @Input() currentPage = 0;

  @Output() onEditAnswer = new EventEmitter<Answer>();
  @Output() onDeleteAnswer = new EventEmitter<Answer>();
  DataType = DataType;
  pageSize = 10;

  actions = [
    {
      icon: 'edit',
      title: 'Edit',
      action: (item: Answer) => this.onEditAnswer.emit(item),
      show: (item: Answer) => true,
    },
    {
      icon: 'delete',
      title: 'Delete',
      action: (item: Answer) => this.onDeleteAnswer.emit(item),
      show: (item: Answer) => true,
    },
  ];

  constructor() {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {}

  editQuestion(question: Answer): void {
    this.onEditAnswer.emit(question);
  }

  deleteQuestion(question: Answer): void {
    this.onDeleteAnswer.emit(question);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.answerAdminDataSource.loadData({
      page: this.currentPage,
      size: this.pageSize,
    });
  }
}
