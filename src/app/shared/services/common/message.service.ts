import { Injectable } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  constructor(private messageService: NzMessageService) {}

  /**
   * Displays an error message and returns an Observable.
   *
   * @param {string} err - the error message to display
   * @return {Observable<any>} - an Observable that emits null
   */
  displayError(err: string): Observable<any> {
    this.messageService.error(err);
    return of(null);
  }

  /**
   * Displays the given message as an information message.
   *
   * @param {string} msg - The message to be displayed.
   * @return {Observable<any>} An observable that emits null.
   */
  displayInfo(msg: string): Observable<any> {
    this.messageService.info(msg);
    return of(null);
  }
}
