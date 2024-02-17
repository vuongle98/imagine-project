export class Notification {
  constructor(
    public id: number,
    public type: NotificationType,
    public title: string,
    public message: string,
    public config?: NotificationConfig
  ) {}
}

export type NotificationConfig = {
  position?: 'top-left' | 'top-right' | 'bottom-left' | 'bottom-right';
  duration?: number;
  showCloseButton?: boolean;
};

export enum NotificationType {
  success = "success",
  warning = "warning",
  error = "error",
  info = "info",
}
