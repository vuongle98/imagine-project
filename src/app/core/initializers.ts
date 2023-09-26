import { APP_INITIALIZER } from '@angular/core';
import { StartupService } from './bootstrap/startup.service';
import { RxStompService } from '@shared/services/rx-stomp/rx-stomp.service';

export function StartupServiceFactory(
  startupService: StartupService,
  rxStompService: RxStompService
) {
  return () => {
    startupService.load();
  };
}

export const AppInitializerProvider = [
  // {
  //   provide: APP_INITIALIZER,
  //   useFactory: (themeService: ThemeService) => () => {
  //     return themeService.loadTheme();
  //   },
  //   deps: [ThemeService],
  //   multi: true,
  // },
  {
    provide: APP_INITIALIZER,
    useFactory: StartupServiceFactory,
    deps: [StartupService, RxStompService],
    multi: true,
  },
];
