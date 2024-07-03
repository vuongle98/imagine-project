import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { BaseCrudComponent } from '@shared/components/base-crud/base-crud.component';
import { FileInfo } from '@shared/models/file';
import { FileAdminDataSource } from '../../services/file-admin.datasource';
import { FileAdminSearchFormComponent } from '../../components/file-admin-search-form/file-admin-search-form.component';
import { DialogService } from '@shared/modules/dialog/dialog.service';
import { NotificationService } from '@shared/services/common/notificaton.service';
import { filter, switchMap, tap } from 'rxjs';
import { FileAdminCreateFormComponent } from '../../components/file-admin-create-form/file-admin-create-form.component';
import { ImageViewerComponent } from '@shared/components/dialog/image-viewer/image-viewer.component';

@Component({
  selector: 'app-file-admin-index',
  templateUrl: './file-admin-index.component.html',
  styleUrls: ['./file-admin-index.component.scss'],
})
export class FileAdminIndexComponent
  extends BaseCrudComponent<FileAdminDataSource>
  implements OnInit, AfterViewInit
{
  listFile: FileInfo[] = [];

  totalRows = 0;
  currentPage = 0;

  @ViewChild(FileAdminSearchFormComponent)
  adminSearchFileForm!: FileAdminSearchFormComponent;

  constructor(
    protected override dialogService: DialogService,
    protected fileAdminDataSource: FileAdminDataSource,
    protected override notificationService: NotificationService
  ) {
    super(dialogService, fileAdminDataSource, notificationService);
  }

  ngOnInit(): void {
    this.fileAdminDataSource.loadData({
      page: this.currentPage,
      size: 10,
    });
    this.fileAdminDataSource.dataSubject
      .pipe(filter((data) => !!data.content))
      .subscribe((res) => {
        this.listFile = res.content;
        this.totalRows = res.totalElements;
      });
  }

  ngAfterViewInit(): void {
    this.adminSearchFileForm.emitSearch
      .pipe(
        tap((value) => {
          this.currentPage = 0;
          this.fileAdminDataSource.loadData({
            ...value,
            page: this.currentPage,
          });
        })
      )
      .subscribe();

    this.adminSearchFileForm.emitCreate
      .pipe(
        switchMap(() =>
          this.openCreate(FileAdminCreateFormComponent, {
            header: 'Create quiz',
            data: {},
          })
        ),
        switchMap((formValue) => this.handleCreateOrUpdate(formValue))
      )
      .subscribe();
  }

  onDeleteFile(file: FileInfo) {
    this.openDelete(
      'Confirm delete file',
      'Do you sure you want to delete this file?',
      file,
      true
    ).subscribe();
  }

  onDownLoadFile(file: FileInfo) {
    this.fileAdminDataSource.downloadFile(file).subscribe();
  }

  onPreViewImage(file: FileInfo) {
    this.dialogService.open(ImageViewerComponent, {
      header: 'Preview image',
      data: file,
      overlayConfig: {maxWidth: '50vw'}
    });
  }
}
