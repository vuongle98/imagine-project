import { Directive, ElementRef, HostListener, Input } from '@angular/core';
import { ImageViewerComponent } from '@shared/components/dialog/image-viewer/image-viewer.component';
import { TestDialogComponent } from '@shared/components/dialog/test-dialog/test-dialog.component';
import { DialogRef } from '@shared/modules/dialog/dialog-ref';
import { DialogService } from '@shared/modules/dialog/dialog.service';

@Directive({
  selector: '[openImageViewer]',
})
export class OpenImageViewerDirective {
  @Input('openImageViewer') data: any;

  constructor(private el: ElementRef, private dialogService: DialogService) {}

  @HostListener('mouseenter')
  onMouseEnter() {
    this.dialogService.open(ImageViewerComponent, {
      header: 'Image preview',
      data: this.data,
    });
  }
}
