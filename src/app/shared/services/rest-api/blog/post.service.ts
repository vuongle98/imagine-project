import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { MessageService } from '@shared/services/common/message.service';
import { Post, PostQuery } from '@shared/models/blog';
import { Observable } from 'rxjs';
import { Pageable } from '@shared/models/utils';

@Injectable({
  providedIn: 'root',
})
export class PostService extends AbstractService {
  apiEndpoint = {
    post: 'api/post',
    featuredPost: 'api/post/featured',
    postWithId: 'api/post/{id}',

    adminPost: 'api/admin/post',
    adminPostWithId: 'api/admin/post/{id}',
  };

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService
  ) {
    super(httpClient);
  }

  searchPost(params: PostQuery): Observable<Pageable<Post[]>> {
    return this.get<Pageable<Post[]>>(this.apiEndpoint.post, {
      queryParams: params,
    });
  }

  findFeaturedPost(params: PostQuery): Observable<Post[]> {
    return this.get<Post[]>(this.apiEndpoint.featuredPost, {
      queryParams: params,
    });
  }

  getPostById(id: string): Observable<Post> {
    return this.get<Post>(this.apiEndpoint.postWithId, {
      pathParams: { id },
    });
  }

  create(post: Post): Observable<Post> {
    return this.post(this.apiEndpoint.post, {
      requestBody: { data: post, type: 'application/json' },
    });
  }

  update(id: string, post: Post): Observable<Post> {
    return this.put<Post>(this.apiEndpoint.postWithId, {
      pathParams: { id },
      requestBody: { data: post, type: 'application/json' },
    });
  }

  deletePost(id: string): Observable<any> {
    return this.delete(this.apiEndpoint.postWithId, {
      pathParams: { id },
    });
  }

  // admin

  adminSearchPost(params: PostQuery): Observable<Pageable<Post[]>> {
    return this.get<Pageable<Post[]>>(this.apiEndpoint.adminPost, {
      queryParams: params,
    });
  }

  adminCreate(post: Post): Observable<Post> {
    return this.post(this.apiEndpoint.adminPost, {
      requestBody: { data: post, type: 'application/json' },
    });
  }

  adminUpdate(id: string, post: Post): Observable<Post> {
    return this.put<Post>(this.apiEndpoint.adminPostWithId, {
      pathParams: { id },
      requestBody: { data: post, type: 'application/json' },
    });
  }

  adminDeletePost(id: string, force?: boolean | undefined): Observable<any> {
    return this.delete(this.apiEndpoint.adminPostWithId, {
      pathParams: { id },
      queryParams: { force },
    });
  }
}
