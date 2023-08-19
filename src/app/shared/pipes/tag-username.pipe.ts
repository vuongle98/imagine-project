import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Observable, map } from 'rxjs';

@Pipe({
  name: 'tagUsername',
})
export class TagUsername implements PipeTransform {
  constructor(
  ) {}

  transform(str: string): string {

    return '';
  }
}
