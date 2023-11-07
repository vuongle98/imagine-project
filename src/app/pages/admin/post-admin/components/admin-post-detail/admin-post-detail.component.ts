import { Component, Input } from '@angular/core';
import { Post } from '@shared/models/blog';

@Component({
  selector: 'app-admin-post-detail',
  templateUrl: './admin-post-detail.component.html',
  styleUrls: ['./admin-post-detail.component.scss']
})
export class AdminPostDetailComponent {

  @Input() post!: Post;

  constructor() {

  }
}
