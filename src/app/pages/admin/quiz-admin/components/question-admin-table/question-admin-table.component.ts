import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
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

  pageSize = 10;
  pageIndex = 1;
  total = 1;

  listQuestion: Question[] = [];

  constructor() {}

  ngOnInit(): void {
    this.questionAdminDatasource.dataSubject
      .pipe(filter((data) => !!data.content))
      .subscribe((res) => {
        this.listQuestion = res.content;
        this.total = res.totalElements;
      });
  }

  ngAfterViewInit(): void {}

  onQueryParamsChange(nzParams: NzTableQueryParams): void {
    const { pageSize, pageIndex } = nzParams;

    const queryParams: QuestionQueryParam = {
      size: pageSize,
      page: pageIndex - 1,
    };

    this.questionAdminDatasource.loadData(queryParams);
  }
}
