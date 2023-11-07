import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DataType, TableAction } from '@shared/modules/table/table.component';
import { FileAdminDataSource } from '../../services/file-admin.datasource';

@Component({
  selector: 'app-file-admin-table',
  templateUrl: './file-admin-table.component.html',
  styleUrls: ['./file-admin-table.component.scss'],
})
export class FileAdminTableComponent {
  @Input() fileAdminDataSource!: FileAdminDataSource;
  @Input() totalRows = 0;
  @Input() currentPage = 0;
  @Input() listFile: any[] = [];

  @Output() onEditFile = new EventEmitter<any>();
  @Output() onDeleteFile = new EventEmitter<any>();
  @Output() onDownloadFile = new EventEmitter<any>();

  pageSize = 10;
  DataType = DataType;

  actions: TableAction[] = [
    {
      icon: 'download',
      title: 'Download',
      action: (item: any) => this.onEditFile.emit(item),
      show: (item: any) => true,
    },
    {
      icon: 'delete',
      title: 'Delete',
      action: (item: any) => this.onDeleteFile.emit(item),
      show: (item: any) => !item.deletedAt,
    },
  ];

  constructor() {}

  onPageChange(page: number) {
    this.currentPage = page;
    this.fileAdminDataSource.loadData({
      page: this.currentPage,
      size: this.pageSize,
    });
  }
}
