import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FileInfo } from '@shared/models/file';
import { FileService } from '@shared/services/rest-api/storage/file.service';
import { tap } from 'rxjs';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css'],
})
export class FileUploadComponent {
  @Input() parentForm!: FormGroup;

  selectedFile!: File;

  constructor(private fileService: FileService) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];

    const uploadForm = new FormData();
    uploadForm.append('file', this.selectedFile);

    this.fileService
      .uploadFile(uploadForm)
      .pipe(
        tap((res: FileInfo) => {
          this.parentForm.patchValue({ fileId: res?.id });
        })
      )
      .subscribe();
  }
}
