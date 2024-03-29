import { DOCUMENT } from '@angular/common';
import {
  AfterViewInit,
  Directive,
  ElementRef,
  HostBinding,
  Inject,
  Input,
  OnChanges,
  OnInit,
  Renderer2,
  ViewContainerRef,
} from '@angular/core';

@Directive({
  selector: '[vg-button]',
})
export class VgButtonDirective {
  @Input() size: 'medium' | 'small' | 'large' | 'xlarge' = 'medium';
  @Input('btn-type') btnType: 'button' | 'link' | 'icon' | 'submit' = 'button';
  @Input() color:
    | 'error'
    | 'primary'
    | 'secondary'
    | 'success'
    | 'warning'
    | 'info' = 'primary';
  @Input() shadow: boolean = false;

  @Input() set loading(value: boolean | null) {
    this.toggle(value || false);
  }

  constructor(
    private viewContainerRef: ViewContainerRef,
    private renderer: Renderer2,
    private elementRef: ElementRef,
    @Inject(DOCUMENT) private document: Document
  ) {
    this.loadComponent();
  }

  loadComponent() {
    this.viewContainerRef.clear();
    this.addSpanText();
  }

  @HostBinding('class')
  get buttonClass(): string {
    if (this.btnType === 'icon') {
      return `button button-${this.color} button-icon`;
    }

    return `button button-${this.size} button-${this.color} button-${this.btnType}`;
  }

  toggle(condition: boolean) {
    condition ? this.showLoading() : this.hideLoading();
  }

  showLoading() {
    const span = document.createElement('span');
    span.classList.add('button-loading');

    this.renderer.appendChild(this.elementRef.nativeElement, span);
  }

  hideLoading() {
    const childs = this.elementRef.nativeElement.children;
    for (let child of childs) {
      if (child.classList.contains('button-loading')) {
        this.renderer.removeChild(this.elementRef.nativeElement, child);
      }
    }
  }

  addSpanText() {
    const buttonText = this.elementRef.nativeElement.innerText;
    this.elementRef.nativeElement.innerText = '';
    const span = document.createElement('span');
    span.textContent = buttonText;

    this.renderer.appendChild(this.elementRef.nativeElement, span);
  }
}
