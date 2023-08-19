import { Component, Input } from '@angular/core';
import { User } from 'src/app/shared/models/user';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {

  @Input() side = 'right';
  @Input() message = '';
  @Input() name!: string;
  @Input() avatar!: string;
  @Input() time = new Date();

  viewProfile() {
    console.log("okie");

  }

}
