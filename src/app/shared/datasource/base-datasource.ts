import { BehaviorSubject } from "rxjs";
import { BaseQueryParam } from "../models/utils";

export abstract class BaseDataSource<T> {
  dataSubject = new BehaviorSubject<T>([] as any);
  protected loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();

  constructor() {}

  abstract loadData(props: BaseQueryParam): void;

}
