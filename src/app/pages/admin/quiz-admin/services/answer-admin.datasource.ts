import { Injectable, OnDestroy } from '@angular/core';
import { LoadingService } from '@shared/components/loading/loading.service';
import { BaseDataSource } from '@shared/datasource/base-datasource';
import { CategoryQuery } from '@shared/models/blog';
import { Answer, AnswerFilter } from '@shared/models/quiz';
import { BaseQueryParam, Pageable } from '@shared/models/utils';
import { AnswerService } from '@shared/services/rest-api/blog/answer.service';
import { CategoryService } from '@shared/services/rest-api/blog/category.service';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AnswerAdminDataSource
  extends BaseDataSource<Pageable<Answer>>
  implements OnDestroy
{
  override dataSubject: BehaviorSubject<Pageable<Answer>> =
    new BehaviorSubject({} as Pageable<Answer>);

  constructor(
    private answerService: AnswerService,
    private loadingService: LoadingService
  ) {
    super();
  }

  override loadData(params: AnswerFilter): void {
    if (!params?.page || !params?.size) {
      params.page = 0;
      params.size = 10;
    }

    const loadCategory$ = this.answerService
      .find(params)
      .pipe(tap((data) => this.dataSubject.next(data)));

    this.loadingService.showLoaderUntilCompleted(loadCategory$).subscribe();
  }

  override create(data: Answer): Observable<Answer> {
    return this.answerService.createAnswer(data);
  }

  override update(id: string, data: any): Observable<Answer> {
    return this.answerService.updateAnswer(id, data);
  }

  override delete(id: string, force?: boolean | undefined): Observable<any> {
    return this.answerService.deleteAnswer(id, force);
  }

  recoverCategory(id: string) {
    return this.update(id, {
      recover: true,
    });
  }

  ngOnDestroy(): void {
    this.dataSubject.complete();
  }
}
