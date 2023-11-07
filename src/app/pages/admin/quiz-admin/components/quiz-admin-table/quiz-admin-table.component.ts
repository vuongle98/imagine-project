import { Component, EventEmitter, Input, Output } from '@angular/core';
import { QuizAdminDataSource } from '../../services/quiz-admin.datasource';
import { Quiz } from 'src/app/shared/models/quiz';
import { DataType } from '@shared/modules/table/table.component';

@Component({
  selector: 'app-quiz-admin-table',
  templateUrl: './quiz-admin-table.component.html',
  styleUrls: ['./quiz-admin-table.component.scss'],
})
export class QuizAdminTableComponent {
  @Input() quizAdminDataSource!: QuizAdminDataSource;
  @Input() totalRows = 1;
  @Input() currentPage = 0;
  @Input() listQuiz: Quiz[] = [];

  @Output() onEditQuiz = new EventEmitter<Quiz>();
  @Output() onDeleteQuiz = new EventEmitter<Quiz>();

  pageSize = 10;

  DataType = DataType;

  actions = [
    {
      icon: 'edit',
      title: 'Edit',
      action: (item: Quiz) => this.onEditQuiz.emit(item),
      show: (item: Quiz) => true,
    },
    {
      icon: 'delete',
      title: 'Delete',
      action: (item: Quiz) => this.onDeleteQuiz.emit(item),
      show: (item: Quiz) => true,
    },
  ];

  editQuiz(quiz: Quiz) {
    this.onEditQuiz.emit(quiz);
  }

  deleteQuiz(quiz: Quiz) {
    this.onDeleteQuiz.emit(quiz);
  }

  onPageChange(page: number) {
    this.currentPage = page;
    this.quizAdminDataSource.loadData({
      page: this.currentPage,
      size: this.pageSize,
    });
  }
}
