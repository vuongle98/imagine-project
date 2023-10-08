import { Injectable } from '@angular/core';
import { LoadingService } from '@shared/components/loading/loading.service';
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

  constructor(
    private questionService: QuestionService,
    private loadingService: LoadingService
  ) {
    super();
  }

  loadData(params: QuestionQueryParam) {
    if (!params?.page || !params?.size) {
      params.page = 0;
      params.size = 10;
    }
    const loadQuestion$ = this.questionService
      .adminFindQuestions(params)
      .pipe(tap((data) => this.dataSubject.next(data)));

    this.loadingService.showLoaderUntilCompleted(loadQuestion$).subscribe();
  }

  override create(data: any): Observable<any> {
    return this.questionService.adminCreateQuestion(data);
  }

  override update(id: string, data: any): Observable<any> {
    return this.questionService.adminUpdateQuestion(id, data);
  }

  override delete(id: string, forever = false): Observable<any> {
    return this.questionService.adminDeleteQuestion(id);
  }

  ngOnDestroy(): void {
    this.dataSubject.complete();
  }
}
