import { Injectable } from '@angular/core';
import {
  Notification,
  NotificationConfig,
  NotificationType,
} from '@shared/models/notification.model';
import { Observable, Subject, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private _notification = new Subject<Notification>();
  private _idx = 0;

  constructor() {}

  getNotification(): Observable<Notification> {
    return this._notification.asObservable();
  }

  info(title: string, message: string, config?: NotificationConfig) {
    this._notification.next(
      new Notification(
        this._idx++,
        NotificationType.info,
        title,
        message,
        config
      )
    );
  }

  success(title: string, message: string, config?: NotificationConfig) {
    this._notification.next(
      new Notification(
        this._idx++,
        NotificationType.success,
        title,
        message,
        config
      )
    );
  }

  warning(title: string, message: string, config?: NotificationConfig) {
    this._notification.next(
      new Notification(
        this._idx++,
        NotificationType.warning,
        title,
        message,
        config
      )
    );
  }

  error(title: string, message: string, config?: NotificationConfig) {
    this._notification.next(
      new Notification(
        this._idx++,
        NotificationType.error,
        title,
        message,
        config
      )
    );
  }
}
