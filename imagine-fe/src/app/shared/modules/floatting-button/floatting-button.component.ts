import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-floatting-button',
  templateUrl: './floatting-button.component.html',
  styleUrls: ['./floatting-button.component.scss'],
})
export class FloattingButtonComponent {
  @Input() iconName = 'comments';
  @Input() isShow = false;
  @Input() position = { bottom: '10px', right: '10px' };
  @Output() showChange = new EventEmitter();

  toggleShow() {
    this.isShow = !this.isShow;
    this.showChange.emit(this.isShow);
  }
}
