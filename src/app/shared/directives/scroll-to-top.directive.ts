import {
  AfterViewInit,
  Directive,
  ElementRef,
  EventEmitter,
  Output,
} from '@angular/core';

@Directive({
  selector: '[checkScrollToTop]',
})
export class ScrollToTOpDirective implements AfterViewInit {
  @Output() scrolledToDiv = new EventEmitter<boolean>();

  private observer!: IntersectionObserver;

  constructor(private el: ElementRef) {}

  ngAfterViewInit(): void {
    this.setupIntersectionObserver();
  }

  ngOnDestroy(): void {
    if (this.observer) {
      this.observer.disconnect();
    }
  }

  private setupIntersectionObserver(): void {
    const topElement = this.el.nativeElement.querySelector('#topElement');
    this.observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting && entry.target === topElement) {
          this.scrolledToDiv.emit(true);
        }
      });
    });

    // Start observing the target div
    this.observer.observe(topElement);
  }
}
