import { Component, Input } from '@angular/core';
import { Post } from '@shared/models/blog';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.scss'],
})
export class ListPostComponent {
  @Input() posts: Post[] = [];

  @Input() columns = 3;

  get gridTemplateColumns() {
    // return `repeat(${this.columns}, 1fr)`;
    const breakpoints = {
      small: '(max-width: 599px)',
      medium: '(min-width: 600px) and (max-width: 899px)',
      large: '(min-width: 900px)',
    };

    // Set different column counts for different screen widths
    return window.matchMedia(breakpoints.small).matches
      ? '1fr'
      : window.matchMedia(breakpoints.medium).matches
      ? 'repeat(2, 1fr)'
      : 'repeat(' + this.columns + ', 1fr)';
  }

  constructor() {}
}
