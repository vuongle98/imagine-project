import { Component, Input } from '@angular/core';
import { Post } from '@shared/models/blog';

@Component({
  selector: 'app-post-compact',
  templateUrl: './post-compact.component.html',
  styleUrls: ['./post-compact.component.scss'],
})
export class PostCompactComponent {
  @Input() post!: Post;
}
