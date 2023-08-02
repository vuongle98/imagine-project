import { Pipe, PipeTransform } from '@angular/core';
import { FileService } from '../services/rest-api/storage/file.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Observable, map } from 'rxjs';

@Pipe({
  name: 'secure',
})
export class SecurePipe implements PipeTransform {
  constructor(
    private fileService: FileService,
    private sanitizer: DomSanitizer
  ) {}

  transform(fileId: string): Observable<SafeUrl> {
    return this.fileService
      .downloadFile(fileId)
      .pipe(
        map((res) =>
          this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(res))
        )
      );
  }
}
