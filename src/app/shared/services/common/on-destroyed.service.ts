import { Injectable, OnDestroy } from '@angular/core';
import { ReplaySubject } from 'rxjs';

@Injectable()
export class OnDestroyService extends ReplaySubject<void> implements OnDestroy {
  constructor() {
    super();
  }

  ngOnDestroy(): void {
    this.next();
    this.complete();
  }
}
