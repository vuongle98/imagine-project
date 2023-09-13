import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RxStompService } from './shared/services/rx-stomp/rx-stomp.service';
import { rxStompServiceFactory } from './shared/services/rx-stomp/rx-stomp-service-factory';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { FormsModule } from '@angular/forms';
import { IconsProviderModule } from './icons-provider.module';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { SharedModule } from './shared/shared.module';
import { LoadingService } from './shared/components/loading/loading.service';
import { JWTInterceptorProvider } from './core/intercepters/jwt-intercepter';
import { ErrorInterceptorProvider } from './core/intercepters/error-interceptor';
import { AppInitializerProvider } from '@core/initializers';

registerLocaleData(en);

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    SharedModule,
    FormsModule,
    IconsProviderModule,
    NzLayoutModule,
    NzMenuModule
  ],
  providers: [
    AppInitializerProvider,
    LoadingService,
    { provide: NZ_I18N, useValue: en_US },
    JWTInterceptorProvider,
    ErrorInterceptorProvider,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
