import { Component } from '@angular/core';
import { PostStore } from '../../services/post.store';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss'],
})
export class IndexComponent {
  featuredPosts$ = this.postStore.featuredPosts$;

  latestPosts$ = this.postStore.latestPosts$;

  constructor(private postStore: PostStore) {}
}
