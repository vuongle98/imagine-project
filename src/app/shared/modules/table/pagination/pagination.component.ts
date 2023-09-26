import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  Output,
} from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss'],
})
export class PaginationComponent {
  @Input() page = 0;
  @Input() totalPage = 0;

  @Output() pageChange = new EventEmitter<number>();

  changePage(page: number) {
    if (page < 0 || page > this.totalPage) {
      return;
    }
    this.page = page;
    this.pageChange.emit(this.page);
  }

  toPage(page: number) {
    this.changePage(page);
  }

  next() {
    this.changePage(this.page + 1);
  }

  prev() {
    this.changePage(this.page - 1);
  }

  last() {
    this.changePage(this.totalPage - 1);
  }

  first() {
    this.changePage(0);
  }
}
