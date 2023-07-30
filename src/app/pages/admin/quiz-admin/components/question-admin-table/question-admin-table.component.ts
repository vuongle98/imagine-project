import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Question, QuestionQueryParam } from 'src/app/shared/models/quiz';
import { QuestionAdminDataSource } from '../../services/quesiton-admin.datasource';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { filter } from 'rxjs';

@Component({
  selector: 'app-question-admin-table',
  templateUrl: './question-admin-table.component.html',
  styleUrls: ['./question-admin-table.component.scss'],
})
export class QuestionAdminTableComponent implements OnInit, AfterViewInit {
  @Input() questionAdminDatasource!: QuestionAdminDataSource;
  @Input() listQuestion: Question[] = [];
  @Input() total = 1;
  @Input() currentPage = 0;

  @Output() onEditQuestion = new EventEmitter<Question>();
  @Output() onDeleteQuestion = new EventEmitter<Question>();
  @Output() emitQueryPageChange = new EventEmitter<NzTableQueryParams>();

  pageSize = 10;
  pageIndex = 1;

  constructor() {}

  ngOnInit(): void {

  }

  ngAfterViewInit(): void {}

  onQueryParamsChange(nzParams: NzTableQueryParams): void {
    this.emitQueryPageChange.emit(nzParams);
  }

  editQuestion(question: Question): void {
    this.onEditQuestion.emit(question);
  }

  deleteQuestion(question: Question): void {
    this.onDeleteQuestion.emit(question);
  }
}
