import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Category } from '@shared/models/blog';
import { DataType, TableAction } from '@shared/modules/table/table.component';
import { CategoryAdminDataSource } from '../../services/category-admin.datasource';

@Component({
  selector: 'app-admin-category-table',
  templateUrl: './admin-category-table.component.html',
  styleUrls: ['./admin-category-table.component.scss'],
})
export class AdminCategoryTableComponent {
  @Input() totalRows = 0;
  @Input() currentPage = 0;
  @Input() listCategory: Category[] = [];
  @Input() categoryAdminDataSource!: CategoryAdminDataSource;

  @Output() onEditCategory = new EventEmitter<Category>();
  @Output() onDeleteCategory = new EventEmitter<Category>();
  @Output() onRecoverCategory = new EventEmitter<Category>();

  pageSize = 10;

  DataType = DataType;

  actions: TableAction[] = [
    {
      icon: 'edit',
      title: 'Edit',
      action: (item: Category) => this.onEditCategory.emit(item),
      show: (item: Category) => true,
    },
    {
      icon: 'delete',
      title: 'Delete',
      action: (item: Category) => this.onDeleteCategory.emit(item),
      show: (item: Category) => !item.deletedAt,
    },
    {
      icon: 'recover',
      title: 'Recover',
      action: (item: Category) => this.onRecoverCategory.emit(item),
      show: (item: Category) => !!item.deletedAt,
    },
  ];

  constructor() {}

  onPageChange(page: number) {
    this.currentPage = page;
    this.categoryAdminDataSource.loadData({
      page: this.currentPage,
      size: this.pageSize,
    });
  }
}
