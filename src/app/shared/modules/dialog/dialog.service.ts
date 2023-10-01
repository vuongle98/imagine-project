import { Overlay, OverlayConfig, OverlayRef } from '@angular/cdk/overlay';
import { Injectable, Injector, Type } from '@angular/core';
import { DialogConfig } from './dialog.config';
import { DialogRef } from './dialog-ref';
import { ComponentPortal, PortalInjector } from '@angular/cdk/portal';
import { DialogContainerComponent } from './dialog-container.components';
import { DIALOG_DATA } from './constants';

@Injectable({ providedIn: 'root' })
export class DialogService {
  private readonly defaultDialogConfig!: DialogConfig;

  constructor(private overlay: Overlay, private readonly injector: Injector) {
    this.defaultDialogConfig = new DialogConfig();
    this.defaultDialogConfig.overlayConfig = new OverlayConfig({
      disposeOnNavigation: true,
      hasBackdrop: true,
      panelClass: 'overlay-panel',
      backdropClass: 'overlay-backdrop',
      scrollStrategy: overlay.scrollStrategies.block(),
      positionStrategy: overlay
        .position()
        .global()
        .centerHorizontally()
        .centerVertically(),
    });
  }

  open<TReturnType = any, TContentComponent = any>(
    component: Type<TContentComponent>,
    config: Partial<DialogConfig>
  ) {
    const mergeConfig = this.getMergeConfig(config);
    console.log(mergeConfig);

    const overlayRef = this.overlay.create(mergeConfig.overlayConfig);
    const dialogRef = new DialogRef<TReturnType, TContentComponent>(overlayRef);

    dialogRef.componentInstance = this.attachDialogContainer(
      overlayRef,
      component,
      dialogRef,
      mergeConfig
    );
    return dialogRef;
  }

  private getMergeConfig(config: Partial<DialogConfig>): DialogConfig {
    if (config == null) {
      return this.defaultDialogConfig;
    }

    return {
      ...this.defaultDialogConfig,
      ...config,
      overlayConfig: {
        ...this.defaultDialogConfig.overlayConfig,
        ...(config?.overlayConfig || {}),
      },
    };
  }

  private attachDialogContainer<TContentComponent = any>(
    overlayRef: OverlayRef,
    component: Type<TContentComponent>,
    dialogRef: DialogRef,
    dialogConfig: DialogConfig
  ) {
    const injector = Injector.create({
      parent: this.injector,
      providers: [
        {
          provide: DialogRef,
          useValue: dialogRef,
        },
        {
          provide: DialogConfig,
          useValue: dialogConfig,
        },
        {
          provide: DIALOG_DATA,
          useValue: dialogConfig.data,
        },
      ],
    });
    const portal = new ComponentPortal(
      DialogContainerComponent,
      null,
      injector
    );
    const containerRef = overlayRef.attach(portal);
    containerRef.instance.contentComponentType = component;
    return containerRef.instance;
  }
}
