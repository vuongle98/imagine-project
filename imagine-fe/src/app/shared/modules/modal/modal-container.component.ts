import {
  Component,
  ComponentRef,
  Type,
  ViewChild,
  ViewContainerRef,
} from '@angular/core';
import { Modal } from '@shared/models/modal.model';

@Component({
  template: `
    <div class="modal-backdrop fade in"></div>
    <ng-template #modalContainer></ng-template>
  `,
})
export class ModalContainerComponent {
  @ViewChild('modalContainer', { read: ViewContainerRef })
  private modalContainer!: ViewContainerRef;

  constructor() {}

  createModal<T extends Modal>(components: Type<T>): ComponentRef<T> {
    this.modalContainer.clear();
    return this.modalContainer.createComponent(components);
  }
}
