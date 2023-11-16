import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LoadingService } from '@shared/components/loading/loading.service';
import { Post } from '@shared/models/blog';
import { PostService } from '@shared/services/rest-api/blog/post.service';
import { Observable, concatMap, finalize, map, switchMap, tap } from 'rxjs';
import { PostStore } from 'src/app/pages/home/services/post.store';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss'],
})
export class PostDetailComponent implements OnInit {
  featuredPosts$: Observable<Post[]> = this.postStore.featuredPosts$;

  latestPosts$: Observable<Post[]> = this.postStore.latestPosts$;

  post$!: Observable<Post>;

  constructor(
    private postService: PostService,
    private router: ActivatedRoute,
    private loadingService: LoadingService,
    private postStore: PostStore
  ) {}

  ngOnInit(): void {
    const postObs$ = this.router.paramMap.pipe(
      map((params) => params.get('id') as string),
      concatMap((postId: string) => this.postService.getPostById(postId)),
      tap(() => {
        this.loadingService.loadingOff();
      })
    );

    this.post$ = this.loadingService.showLoaderUntilCompleted(postObs$);
  }
}
