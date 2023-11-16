import {
  AfterViewInit,
  Component,
  ContentChildren,
  QueryList,
} from '@angular/core';
import { SideDirective } from '../directives/side.directive';

@Component({
  selector: 'app-blog-layout',
  templateUrl: './blog-layout.component.html',
  styleUrls: ['./blog-layout.component.scss'],
})
export class BlogLayoutComponent implements AfterViewInit {
  @ContentChildren(SideDirective) sides!: QueryList<SideDirective>;

  leftSide: SideDirective[] = [];
  rightSide: SideDirective[] = [];

  constructor() {}

  ngAfterViewInit(): void {
    this.leftSide = this.sides.filter((side) => side.side === 'left');
    this.rightSide = this.sides.filter((side) => side.side === 'right');
  }
}
