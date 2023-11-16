import { Component, Input } from '@angular/core';
import { Post } from '@shared/models/blog';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})
export class PostDetailComponent {

  @Input() post!: Post;

  constructor() {}

}
