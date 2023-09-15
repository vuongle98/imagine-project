import { Component } from '@angular/core';
import { Notification } from '@shared/models/notification.model';
import { NotificationService } from '@shared/services/common/notificaton.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss'],
})
export class NotificationComponent {
  notifications: Notification[] = [];

  private _notificationSub!: Subscription;

  constructor(private _notificationService: NotificationService) {}

  private _addNotification(notification: Notification) {
    let duration = notification.config?.duration || 3000;

    if (duration < 0) {
      duration = 3000;
    }

    duration = duration > 10000 ? 10000 : duration;

    this.notifications.push(notification);

    setTimeout(() => this.close(notification), duration);
  }

  ngOnInit() {
    this._notificationSub = this._notificationService
      .getNotification()
      .subscribe((notification) => {
        notification.config = {
          duration: notification.config?.duration || 3000,
          position: notification.config?.position || 'top-right',
          ...notification.config,
        };

        this._addNotification(notification);
      });
  }

  ngOnDestroy() {
    this._notificationSub.unsubscribe();
  }

  close(notification: Notification) {
    this.notifications = this.notifications.filter(
      (n) => n.id !== notification.id
    );
  }

  getNotificationClass(notification: Notification) {
    let className = `notification notification-${notification.type}`;

    if (notification.config) {
      className += ` notification-${notification.config.position}`;
    }

    return className;
  }
}
