import { Injectable } from '@angular/core';
import { Observable, concatMap, defer, delay, expand, finalize, from, map, of, switchMap, takeWhile, toArray } from 'rxjs';
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
    fileChunk: 'api/file/chunk',
    fileChunkMerge: 'api/file/chunk/merge',
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

  uploadFileWithChunk(file: File, chunkSize: number): Observable<any> {
    const identifier = this.hashString(file.name);
    const totalChunks = Math.ceil(file.size / chunkSize);
    return this.handleUploadChunks(file, chunkSize);
  }

  handleUploadChunks(file: File, chunkSize: number) {

    const totalChunks = Math.ceil(file.size / chunkSize);
    const identifier = this.hashString(file.name).toString();

    let currentChunk = 0;

    const uploadChunk = (): Observable<any> => {
      if (currentChunk >= totalChunks) {
        return of({ done: true });
      }

      const start = currentChunk * chunkSize;
      const end = Math.min(start + chunkSize, file.size);
      const chunk = file.slice(start, end);

      const formData = this.makeChunkFormData(chunk, currentChunk, totalChunks, identifier);

      return this.uploadChunk(formData).pipe(map(res => {
        currentChunk++;
        return { done: false, res };
      }));
    }

    return of({}).pipe(
      expand(() => uploadChunk()),
      takeWhile(res => !res.done, true),
      toArray(),
      switchMap(() =>
        this.mergeChunk({
          identifier,
          'contentType': file.type,
          'totalChunks': totalChunks,
          'fileName': file.name,
          'size': file.size
        }))
    )
    // const hashCode = (s: string) => s.split('').reduce((a, b) => (((a << 5) - a) + b.charCodeAt(0)) | 0, 0);
  }

  makeChunks(file: File, chunkIndex: number, chunkSize: number, timeLoop: number): Blob[] {
    const chunks: Blob[] = [];

    for (let time = 0; time < timeLoop; time++) {
      const chunkFile = file.slice(chunkIndex * chunkSize, (chunkIndex + 1) * chunkIndex);
      chunks.push(chunkFile);
      chunkIndex++;
    }

    return chunks;
  }

  makeChunkFormData(chunk: Blob, chunkIndex: number, totalChunks: number, identifier: string) {
    const formData = new FormData();
    formData.append('file', chunk);
    formData.append('chunk', (chunkIndex + 1).toString());
    formData.append('totalChunks', totalChunks.toString());
    formData.append('identifier', identifier.toString());

    return formData
  }

  hashString(input: string) {
    let hash = 0, i, chr;
    if (input.length === 0) return hash;

    for (i = 0; i < input.length; i++) {
      chr = input.charCodeAt(i);
      hash = ((hash << 5) - hash) + chr;
      hash |= 0; // Convert to 32bit integer
    }
    return hash;
  }

  uploadChunk(formData: FormData): Observable<any> {
    return this.post(this.apiEndpoint.fileChunk, {
      requestBody: { type: 'multipart/form-data', data: formData },
    });
  }

  mergeChunk(data: any) {
    return this.post(this.apiEndpoint.fileChunkMerge, {
      requestBody: { type: 'application/json', data },
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
