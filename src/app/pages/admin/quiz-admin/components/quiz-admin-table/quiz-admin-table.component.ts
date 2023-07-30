import { Component, EventEmitter, Input, Output } from '@angular/core';
import { QuizAdminDataSource } from '../../services/quiz-admin.datasource';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { Quiz } from 'src/app/shared/models/quiz';

@Component({
  selector: 'app-quiz-admin-table',
  templateUrl: './quiz-admin-table.component.html',
  styleUrls: ['./quiz-admin-table.component.scss'],
})
export class QuizAdminTableComponent {
  @Input() quizAdminDataSource!: QuizAdminDataSource;
  @Input() total = 1;
  @Input() currentPage = 0;
  @Input() listQuiz: Quiz[] = [];

  @Output() onEditQuiz = new EventEmitter<Quiz>();
  @Output() onDeleteQuiz = new EventEmitter<Quiz>();
  @Output() emitQueryPageChange = new EventEmitter<NzTableQueryParams>();

  pageIndex = 1;
  pageSize = 10;

  onQueryParamsChange(queryParams: NzTableQueryParams) {
    this.emitQueryPageChange.emit(queryParams);
  }

  editQuiz(quiz: Quiz) {
    this.onEditQuiz.emit(quiz);
  }

  deleteQuiz(quiz: Quiz) {
    this.onDeleteQuiz.emit(quiz);
  }
}
