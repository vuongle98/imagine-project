import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  Observable,
  Subject,
  filter,
  finalize,
  tap,
} from 'rxjs';
import { BaseDataSource } from 'src/app/shared/datasource/base-datasource';
import { Question, QuestionQueryParam } from 'src/app/shared/models/quiz';
import { Pageable } from 'src/app/shared/models/utils';
import { QuestionService } from 'src/app/shared/services/rest-api/quiz/question.service';

@Injectable({
  providedIn: 'root',
})
export class QuestionAdminDataSource extends BaseDataSource<
  Pageable<Question[]>
> {
  override dataSubject: BehaviorSubject<Pageable<Question[]>> =
    new BehaviorSubject({} as Pageable<Question[]>);

  constructor(private questionService: QuestionService) {
    super();
  }

  loadData(params: QuestionQueryParam) {
    if (!params?.page || !params?.size) {
      params.page = 0;
      params.size = 10;
    }
    this.loadingSubject.next(true);
    this.questionService
      .adminFindQuestions(params)
      .pipe(
        tap((data) => this.dataSubject.next(data)),
        finalize(() => this.loadingSubject.next(false))
      )
      .subscribe();
  }

  ngOnDestroy(): void {
    this.dataSubject.complete();
    this.loadingSubject.complete();
  }
}
