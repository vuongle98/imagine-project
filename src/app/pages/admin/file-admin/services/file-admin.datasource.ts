import { Injectable, OnDestroy } from '@angular/core';
import { LoadingService } from '@shared/components/loading/loading.service';
import { BaseDataSource } from '@shared/datasource/base-datasource';
import { FileInfo, FileQuery } from '@shared/models/file';
import { Pageable } from '@shared/models/utils';
import { FileService } from '@shared/services/rest-api/storage/file.service';
import { BehaviorSubject, Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FileAdminDataSource
  extends BaseDataSource<Pageable<FileInfo[]>>
  implements OnDestroy
{
  override dataSubject: BehaviorSubject<Pageable<FileInfo[]>> =
    new BehaviorSubject({} as Pageable<FileInfo[]>);

  constructor(
    private fileService: FileService,
    private loadingService: LoadingService
  ) {
    super();
  }

  override loadData(params: FileQuery): void {
    if (!params?.page || !params?.size) {
      params.page = 0;
      params.size = 10;
    }

    const loadCategory$ = this.fileService
      .findFileAdmin(params)
      .pipe(tap((data) => this.dataSubject.next(data)));

    this.loadingService.showLoaderUntilCompleted(loadCategory$).subscribe();
  }

  override create(data: FormData): Observable<FileInfo> {
    return this.fileService.uploadFile(data);
  }

  override update(id: string, data: any): Observable<any> {
    return of(null);
  }

  override delete(id: string, force?: boolean | undefined): Observable<any> {
    return this.fileService.deleteFileAdmin(id, force);
  }

  ngOnDestroy(): void {
    this.dataSubject.complete();
  }
}
