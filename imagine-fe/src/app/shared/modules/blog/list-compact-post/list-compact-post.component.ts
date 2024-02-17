import { Component, Input } from '@angular/core';
import { Post } from '@shared/models/blog';

@Component({
  selector: 'app-list-compact-post',
  templateUrl: './list-compact-post.component.html',
  styleUrls: ['./list-compact-post.component.scss']
})
export class ListPostCompactComponent {

  @Input() posts: Post[] = [];

}
