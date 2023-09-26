import { BehaviorSubject } from "rxjs";
import { BaseQueryParam } from "../models/utils";

export abstract class BaseDataSource<T> {
  dataSubject = new BehaviorSubject<T>([] as any);

  constructor() {}

  abstract loadData(props: BaseQueryParam): void;

}
