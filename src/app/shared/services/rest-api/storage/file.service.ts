import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class FileService extends AbstractService {
  apiEndpoint = {
    file: 'api/file',
    fileDownloadWithId: 'api/file/download/{id}',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  downloadFile(id: string): Observable<any> {
    return this.get(this.apiEndpoint.fileDownloadWithId + '?get-byte=true', { pathParams: { id }, responseType: 'blob' as 'json' });
  }

  buildDownloadUrl(id: string): string {
    return this.apiEndpoint.file + '/download/' + id;
  }
}
