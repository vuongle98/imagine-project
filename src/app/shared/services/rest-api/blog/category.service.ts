import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { MessageService } from '@shared/services/common/message.service';
import { Observable, filter, shareReplay } from 'rxjs';
import { Category, CategoryQuery } from '@shared/models/blog';
import { Pageable } from '@shared/models/utils';

@Injectable({
  providedIn: 'root',
})
export class CategoryService extends AbstractService {
  apiEndpoint = {
    category: 'api/admin/category',
    categoryWithId: 'api/admin/category/{id}',
  };

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService
  ) {
    super(httpClient);
  }

  findCategories(params: CategoryQuery): Observable<Pageable<Category[]>> {
    return this.get<Pageable<Category[]>>(this.apiEndpoint.category, {
      queryParams: params,
    }).pipe(
      filter((res) => !!res),
      shareReplay()
    );
  }

  createCategory(category: Category): Observable<Category> {
    return this.post<Category>(this.apiEndpoint.category, {
      requestBody: { type: 'application/json', data: category },
    });
  }

  updateCategory(id: string, category: Category): Observable<Category> {
    return this.put<Category>(this.apiEndpoint.categoryWithId, {
      pathParams: { id },
      requestBody: { type: 'application/json', data: category },
    });
  }

  deleteCategory(id: string, force = false): Observable<any> {
    return this.delete(this.apiEndpoint.categoryWithId, { pathParams: { id }, queryParams: { force } });
  }
}
