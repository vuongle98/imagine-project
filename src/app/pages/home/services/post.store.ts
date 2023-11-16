import { Injectable } from '@angular/core';
import { LoadingService } from '@shared/components/loading/loading.service';
import { Post, PostQuery } from '@shared/models/blog';
import { Pageable } from '@shared/models/utils';
import { PostService } from '@shared/services/rest-api/blog/post.service';
import { BehaviorSubject, Observable, concatMap, map, of, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PostStore {
  private _featuredPosts$ = new BehaviorSubject<Post[]>([]);

  featuredPosts$ = this._featuredPosts$.asObservable();

  private _latestPosts$ = new BehaviorSubject<Post[]>([]);

  latestPosts$ = this._latestPosts$.asObservable();

  constructor(
    private postService: PostService,
    private loadingService: LoadingService
  ) {
    const loadPosts = of(null).pipe(
      concatMap(() => this.loadLatestPosts({ page: 0, size: 5 })),
      concatMap(() => this.loadFeatutedPost())
    );

    loadingService.showLoaderUntilCompleted(loadPosts).subscribe();
  }

  loadFeatutedPost(): Observable<Post[]> {
    return this.postService
      .findFeaturedPost({})
      .pipe(tap((posts) => this._featuredPosts$.next(posts)));
  }

  loadLatestPosts(pageable: PostQuery): Observable<Post[]> {
    return this.postService.searchPost(pageable).pipe(
      tap((posts) => this._latestPosts$.next(posts.content)),
      map((posts) => posts.content)
    );
  }
}
