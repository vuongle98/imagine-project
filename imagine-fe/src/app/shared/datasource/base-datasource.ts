import { BehaviorSubject, Observable } from 'rxjs';
import { BaseQueryParam } from '../models/utils';

export abstract class BaseDataSource<T> {
  dataSubject = new BehaviorSubject<T>([] as any);

  constructor() {}

  abstract loadData(props: BaseQueryParam): void;

  abstract create(data: any): Observable<any>;

  abstract update(id: string, data: any): Observable<any>;

  abstract delete(id: string, forever?: boolean): Observable<any>;
}
