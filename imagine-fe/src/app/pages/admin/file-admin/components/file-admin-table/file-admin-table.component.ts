import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DataType, TableAction } from '@shared/modules/table/table.component';
import { FileAdminDataSource } from '../../services/file-admin.datasource';
import { FileInfo } from '@shared/models/file';

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

  @Output() onPreViewImage = new EventEmitter<any>();
  @Output() onDeleteFile = new EventEmitter<any>();
  @Output() onDownloadFile = new EventEmitter<any>();

  pageSize = 10;
  DataType = DataType;

  actions: TableAction[] = [
    {
      icon: 'download',
      title: 'Download',
      action: (item: any) => this.onDownloadFile.emit(item),
      show: (item: any) => true,
    },
    {
      icon: 'delete',
      title: 'Delete',
      action: (item: any) => this.onDeleteFile.emit(item),
      show: (item: any) => !item.deletedAt,
    },
    {
      icon: 'preview',
      title: 'Preview',
      action: (item: any) => this.onPreViewImage.emit(item),
      show: (item: any) => this.isImage(item),
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

  isImage(item: FileInfo) {
    return (
      item.extension === 'jpg' ||
      item.extension === 'png' ||
      item.extension === 'jpeg'
    );
  }
}
