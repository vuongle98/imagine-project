import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class StatisticService extends AbstractService {
  apiEndpoint = {
    statistic: 'api/admin/statistic',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  getStatistic(): Observable<any> {
    return this.get<any>(this.apiEndpoint.statistic, { queryParams: {} });
  }
}
