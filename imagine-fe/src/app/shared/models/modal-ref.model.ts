import { Component, ComponentRef } from '@angular/core';
import { ModalContainerComponent } from '@shared/modules/modal/modal-container.component';
import { Observable, Subject } from 'rxjs';
import { Modal } from './modal.model';
import { ChattingContainerComponent } from '@shared/modules/modal/chatting-container.component';

export class ModalRef {
  private results = new Subject<any>();

  constructor(
    private modalContainer: ComponentRef<ChattingContainerComponent>,
    private modal: ComponentRef<Modal>
  ) {
    this.modal.instance.modalInstance = this;
  }

  close(output: any): void {
    this.results.next(output);
    this.destroy$();
  }

  dismiss(output: any): void {
    this.results.next(output);
    this.destroy$();
  }

  onResults(): Observable<any> {
    return this.results.asObservable();
  }

  private destroy$(): void {
    this.modalContainer.destroy();
    this.modalContainer.destroy();
    this.results.complete();
  }
}
