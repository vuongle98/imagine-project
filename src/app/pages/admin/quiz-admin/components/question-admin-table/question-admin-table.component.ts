import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { Question } from 'src/app/shared/models/quiz';
import { QuestionAdminDataSource } from '../../services/quesiton-admin.datasource';
import { DataType, TableData } from '@shared/modules/table/table.component';

@Component({
  selector: 'app-question-admin-table',
  templateUrl: './question-admin-table.component.html',
  styleUrls: ['./question-admin-table.component.scss'],
})
export class QuestionAdminTableComponent implements OnInit, AfterViewInit {
  @Input() questionAdminDatasource!: QuestionAdminDataSource;
  @Input() listQuestion: Question[] = [];
  @Input() totalRows = 1;
  @Input() currentPage = 0;

  @Output() onEditQuestion = new EventEmitter<Question>();
  @Output() onDeleteQuestion = new EventEmitter<Question>();

  pageSize = 10;

  DataType = DataType;

  actions = [
    {
      icon: 'edit',
      title: 'Edit',
      action: (item: Question) => this.onEditQuestion.emit(item),
      show: (item: Question) => true,
    },
    {
      icon: 'delete',
      title: 'Delete',
      action: (item: Question) => this.onDeleteQuestion.emit(item),
      show: (item: Question) => true,
    },
  ];

  constructor() {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {}

  editQuestion(question: Question): void {
    this.onEditQuestion.emit(question);
  }

  deleteQuestion(question: Question): void {
    this.onDeleteQuestion.emit(question);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.questionAdminDatasource.loadData({
      page: this.currentPage,
      size: this.pageSize,
    });
  }
}
