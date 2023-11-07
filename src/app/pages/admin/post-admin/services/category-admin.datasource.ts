import { Injectable, OnDestroy } from '@angular/core';
import { LoadingService } from '@shared/components/loading/loading.service';
import { BaseDataSource } from '@shared/datasource/base-datasource';
import { Category, CategoryQuery } from '@shared/models/blog';
import { BaseQueryParam, Pageable } from '@shared/models/utils';
import { CategoryService } from '@shared/services/rest-api/blog/category.service';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CategoryAdminDataSource
  extends BaseDataSource<Pageable<Category[]>>
  implements OnDestroy
{
  override dataSubject: BehaviorSubject<Pageable<Category[]>> =
    new BehaviorSubject({} as Pageable<Category[]>);

  constructor(
    private categoryService: CategoryService,
    private loadingService: LoadingService
  ) {
    super();
  }

  override loadData(params: CategoryQuery): void {
    if (!params?.page || !params?.size) {
      params.page = 0;
      params.size = 10;
    }

    params.getDeleted = true;

    const loadCategory$ = this.categoryService
      .findCategories(params)
      .pipe(tap((data) => this.dataSubject.next(data)));

    this.loadingService.showLoaderUntilCompleted(loadCategory$).subscribe();
  }

  override create(data: Category): Observable<Category> {
    return this.categoryService.createCategory(data);
  }

  override update(id: string, data: any): Observable<Category> {
    return this.categoryService.updateCategory(id, data);
  }

  override delete(id: string, force?: boolean | undefined): Observable<any> {
    return this.categoryService.deleteCategory(id, force);
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
