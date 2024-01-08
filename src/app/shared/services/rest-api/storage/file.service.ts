import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { FileInfo, FileQuery } from '@shared/models/file';
import { Pageable } from '@shared/models/utils';

@Injectable({
  providedIn: 'root',
})
export class FileService extends AbstractService {
  apiEndpoint = {
    file: 'api/file',
    fileWithId: 'api/file/{id}',
    fileDownloadWithId: 'api/file/{id}/download',

    fileAdmin: 'api/admin/file',
    fileAdminWithId: 'api/admin/file/{id}',
    fileAdminDownloadWithId: 'api/admin/file/{id}/download',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  downloadFile(id: string): Observable<any> {
    return this.get(this.apiEndpoint.fileDownloadWithId + '?get-byte=true', {
      pathParams: { id },
      responseType: 'blob' as 'json',
    });
  }

  buildDownloadUrl(id: string): string {
    return this.apiEndpoint.file + '/download/' + id;
  }

  findFile(params: FileQuery): Observable<Pageable<FileInfo>> {
    return this.get<Pageable<FileInfo>>(this.apiEndpoint.file, {
      queryParams: params,
    });
  }

  uploadFile(formData: FormData): Observable<any> {
    return this.post(this.apiEndpoint.file, {
      requestBody: { type: 'multipart/form-data', data: formData },
    });
  }

  getFileInfo(id: string): Observable<FileInfo> {
    return this.get<FileInfo>(this.apiEndpoint.fileWithId, {
      pathParams: { id },
    });
  }

  deleteFile(id: string): Observable<any> {
    return this.delete(this.apiEndpoint.fileAdminWithId, {
      pathParams: { id },
    });
  }

  findFileAdmin(params: FileQuery): Observable<Pageable<FileInfo>> {
    return this.get<Pageable<FileInfo>>(this.apiEndpoint.fileAdmin, {
      queryParams: params,
    });
  }

  adminUploadFile(formData: FormData): Observable<any> {
    return this.post(this.apiEndpoint.fileAdmin, {
      requestBody: { type: 'multipart/form-data', data: formData },
    });
  }

  getFileInfoAdmin(id: string): Observable<FileInfo> {
    return this.get<FileInfo>(this.apiEndpoint.fileAdminWithId, {
      pathParams: { id },
    });
  }

  deleteFileAdmin(id: string, force = false): Observable<any> {
    return this.delete(this.apiEndpoint.fileAdminWithId, {
      pathParams: { id },
      queryParams: { force },
    });
  }
}
