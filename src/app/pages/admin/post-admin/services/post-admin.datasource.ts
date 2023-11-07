import { Injectable, OnDestroy } from '@angular/core';
import { LoadingService } from '@shared/components/loading/loading.service';
import { BaseDataSource } from '@shared/datasource/base-datasource';
import { Post, PostQuery } from '@shared/models/blog';
import { Pageable } from '@shared/models/utils';
import { PostService } from '@shared/services/rest-api/blog/post.service';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PostAdminDataSource
  extends BaseDataSource<Pageable<Post[]>>
  implements OnDestroy
{
  override dataSubject: BehaviorSubject<Pageable<Post[]>> = new BehaviorSubject(
    {} as Pageable<Post[]>
  );

  constructor(
    private postService: PostService,
    private loadingService: LoadingService
  ) {
    super();
  }

  override loadData(params: PostQuery): void {
    if (!params?.page || !params?.size) {
      params.page = 0;
      params.size = 10;
    }

    params.getDeleted = true;

    const loadCategory$ = this.postService
      .adminSearchPost(params)
      .pipe(tap((data) => this.dataSubject.next(data)));

    this.loadingService.showLoaderUntilCompleted(loadCategory$).subscribe();
  }

  override create(data: Post): Observable<Post> {
    return this.postService.adminCreate(data);
  }

  override update(id: string, data: any): Observable<Post> {
    return this.postService.adminUpdate(id, data);
  }

  override delete(id: string, force?: boolean | undefined): Observable<any> {
    return this.postService.adminDeletePost(id, force);
  }

  recoverPost(id: string) {
    return this.update(id, {
      recover: true,
    });
  }

  ngOnDestroy(): void {
    this.dataSubject.complete();
  }
}
