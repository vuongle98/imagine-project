import { Component, Input } from '@angular/core';
import { Post } from '@shared/models/blog';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent {

  @Input() item!: Post;

  constructor() {}

}
