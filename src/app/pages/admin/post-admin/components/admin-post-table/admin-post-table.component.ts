import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PostAdminDataSource } from '../../services/post-admin.datasource';
import { Post } from '@shared/models/blog';
import { DataType, TableAction } from '@shared/modules/table/table.component';

@Component({
  selector: 'app-admin-post-table',
  templateUrl: './admin-post-table.component.html',
  styleUrls: ['./admin-post-table.component.scss'],
})
export class AdminPostTableComponent {
  @Input() postAdminDataSource!: PostAdminDataSource;
  @Input() totalRows = 0;
  @Input() currentPage = 0;
  @Input() listPost: Post[] = [];

  @Output() onEditPost = new EventEmitter<Post>();
  @Output() onDeletePost = new EventEmitter<Post>();
  @Output() onRecoverPost = new EventEmitter<Post>();
  @Output() onSetFeaturePost = new EventEmitter<Post>();
  @Output() onUnsetFeaturePost = new EventEmitter<Post>();

  pageSize = 10;

  DataType = DataType;

  actions: TableAction[] = [
    {
      icon: 'edit',
      title: 'Edit',
      action: (item: Post) => this.onEditPost.emit(item),
      show: (item: Post) => true,
    },
    {
      icon: 'delete',
      title: 'Delete',
      action: (item: Post) => this.onDeletePost.emit(item),
      show: (item: Post) => !item.deletedAt,
    },
    {
      icon: 'recover',
      title: 'Recover',
      action: (item: Post) => this.onRecoverPost.emit(item),
      show: (item: Post) => !!item.deletedAt,
    },
    {
      icon: 'feature',
      title: 'Feature',
      action: (item: Post) => this.onSetFeaturePost.emit(item),
      show: (item: Post) => !item.featured,
    },
    {
      icon: 'unfeature',
      title: 'Unfeature',
      action: (item: Post) => this.onUnsetFeaturePost.emit(item),
      show: (item: Post) => item.featured,
    }
  ];

  constructor() {}

  onPageChange(page: number) {
    this.currentPage = page;
    this.postAdminDataSource.loadData({
      page: this.currentPage,
      size: this.pageSize,
    });
  }
}
