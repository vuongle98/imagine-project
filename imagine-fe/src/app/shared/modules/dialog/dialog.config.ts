import { OverlayConfig } from '@angular/cdk/overlay';
import { Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class DialogConfig<T = any> {
  header = '';
  closeable = true;
  containerAnimationTiming = 0.3;
  contentAnimationTiming = 0.2;
  animationChildDelay = 0;
  data?: T;
  overlayConfig?: OverlayConfig;
}
