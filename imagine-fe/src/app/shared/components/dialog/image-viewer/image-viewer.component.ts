import { Component, Inject } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { FileInfo } from '@shared/models/file';
import { DIALOG_DATA } from '@shared/modules/dialog/constants';
import { FileService } from '@shared/services/rest-api/storage/file.service';
import { tap } from 'rxjs';

@Component({
  selector: 'app-image-viewer',
  templateUrl: './image-viewer.component.html',
  styleUrls: ['./image-viewer.component.scss'],
})
export class ImageViewerComponent {
  imageUrl: any;

  constructor(
    private fileService: FileService,
    @Inject(DIALOG_DATA) public data: FileInfo,
    private sanitizer: DomSanitizer
  ) {
    fileService
      .downloadFile(data.id)
      .pipe(
        tap((res: Blob) => {
          this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(
            URL.createObjectURL(res)
          );
        })
      )
      .subscribe();
  }
}
