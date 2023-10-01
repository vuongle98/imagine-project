import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { FormsModule } from '@angular/forms';
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
    BrowserAnimationsModule,
    HttpClientModule,
    SharedModule,
    FormsModule,
  ],
  providers: [
    AppInitializerProvider,
    LoadingService,
    JWTInterceptorProvider,
    ErrorInterceptorProvider,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
