import {
  AfterViewInit,
  ChangeDetectionStrategy,
  Component,
  ComponentRef,
  ViewChild,
  Type,
  ChangeDetectorRef,
  HostBinding,
  HostListener,
  EventEmitter,
} from '@angular/core';
import { DialogConfig } from './dialog.config';
import { AnimationState, fadeAnimation, zoomAnimation } from './constants';
import { DialogContentDirective } from './dialog-content.directive';
import { DialogRef } from './dialog-ref';
import { AnimationEvent } from '@angular/animations';

@Component({
  selector: 'app-dialog-container',
  template: `<div
    class="dialog-container"
    [@zoom]="{
      value: animationState,
      params: { timing: dialogConfig.containerAnimationTiming }
    }"
  >
    <div dialogHeader>
      <div>{{ dialogConfig.header }}</div>
      <button vg-button btn-type="icon" color="secondary" dialogClose>x</button>
    </div>
    <ng-container dialogContent></ng-container>
  </div>`,
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [zoomAnimation(), fadeAnimation()],
})
export class DialogContainerComponent<TContentComponent = any>
  implements AfterViewInit
{
  @ViewChild(DialogContentDirective)
  contentInsertionPoint!: DialogContentDirective;

  animationState = AnimationState.Enter;
  animationStateChanged = new EventEmitter<AnimationEvent>();
  contentComponentType!: Type<TContentComponent>;

  private contentComponentRef!: ComponentRef<TContentComponent>;

  @HostBinding('@fade') get hostAnimation() {
    return {
      value: this.animationState,
      params: {
        timing: this.dialogConfig.containerAnimationTiming,
        delayChild: this.dialogConfig.animationChildDelay,
      },
    };
  }

  @HostListener('@fade.start', ['$event']) onAnimationStart(
    event: AnimationEvent
  ) {
    this.animationStateChanged.emit(event);
  }

  @HostListener('@fade.done', ['$event']) onAnimationDone(
    event: AnimationEvent
  ) {
    this.animationStateChanged.emit(event);
  }

  constructor(
    public readonly dialogConfig: DialogConfig,
    private readonly dialogRef: DialogRef,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngAfterViewInit(): void {
    this.loadContentComponent();

    this.cdr.detectChanges();
  }

  ngOnDestroy() {
    this.contentComponentRef?.destroy();
  }

  loadContentComponent() {
    if (!this.contentComponentRef) {
      const vcr = this.contentInsertionPoint.viewContainerRef;
      vcr.clear();
      this.contentComponentRef = vcr.createComponent(this.contentComponentType);
    }
  }

  onClickCloseDialog() {
    this.closeDialog();
  }

  private closeDialog() {
    this.dialogRef.close();
  }

  startExitAnimation() {
    this.animationState = AnimationState.Leave;
    this.cdr.markForCheck();
  }
}
