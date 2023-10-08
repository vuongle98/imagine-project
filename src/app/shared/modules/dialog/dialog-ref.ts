import { OverlayRef } from '@angular/cdk/overlay';
import { ESCAPE, hasModifierKey } from '@angular/cdk/keycodes';
import { Observable, Subject, filter, map, race, take } from 'rxjs';
import { DialogContainerComponent } from './dialog-container.components';
import { AnimationState } from './constants';
import { DialogConfig } from './dialog.config';

const AnimationPhase = {
  START: 'start',
  DONE: 'done',
};

export class DialogRef<TReturnType = any, TContentComponent = any> {
  private readonly beforeClosed$ = new Subject();
  private readonly afterClosed$ = new Subject<TReturnType>();
  result?: TReturnType;

  componentInstance!: DialogContainerComponent<TContentComponent>;

  constructor(private readonly overlayRef: OverlayRef, config: DialogConfig) {
    if (config.closeable) {
      race(
        overlayRef.backdropClick(),
        overlayRef
          .keydownEvents()
          .pipe(filter((event) => event.key === 'Escape'))
      )
        .pipe(take(1))
        .subscribe(() => this.close());
    }

    overlayRef.detachments().subscribe(() => {
      this.afterClosed$.next(this.result as TReturnType);
      this.afterClosed$.complete();
      this.componentInstance = null as any;
    });
  }

  public beforeClose(): Observable<any> {
    return this.beforeClosed$.asObservable();
  }

  public afterClose(): Observable<TReturnType> {
    return this.afterClosed$.asObservable();
  }

  close(data?: TReturnType): void {
    this.result = data;
    this.componentInstance.animationStateChanged
      .pipe(
        filter((event) => event.phaseName === AnimationPhase.START),
        take(1)
      )
      .subscribe(() => {
        this.overlayRef.detachBackdrop();
        this.beforeClosed$.next(0);
        this.beforeClosed$.complete();
      });

    this.componentInstance.animationStateChanged
      .pipe(
        filter(
          (event) =>
            event.phaseName === AnimationPhase.DONE &&
            event.toState === AnimationState.Leave
        ),
        take(1)
      )
      .subscribe(this.overlayRef.dispose.bind(this.overlayRef));

    this.componentInstance.startExitAnimation();
  }
}
