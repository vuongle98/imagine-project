import { AfterViewInit, Component, Input, TemplateRef } from '@angular/core';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
export class CardComponent {
  @Input() item: any

  @Input()
  actions: Array<TemplateRef<any>> = [];

  @Input()
  cover?: TemplateRef<any>;

}
