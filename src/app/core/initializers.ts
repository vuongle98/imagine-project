import { APP_INITIALIZER } from "@angular/core";
import { StartupService } from "./bootstrap/startup.service";
import { ThemeService } from "../theme.service";



export function StartupServiceFactory(startupService: StartupService) {
  return () => {
    // startupService.
  }
}

// export const AppInitializerProvider = {
//   provide: APP_INITIALIZER,
//   useFactory: (themeService: ThemeService) => () => {
//     return themeService.loadTheme();
//   },
//   deps: [ThemeService],
//   multi: true,
// };
