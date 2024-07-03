import { NgModule } from '@angular/core';
import { ServerModule } from '@angular/platform-server';

import { AppModule } from './app.module';
import { HomeComponent } from './app/component/home/home.component';
import { RegisterComponent } from './app/component/register/register.component';
import { LoginComponent } from './app/component/login/login.component';
import { ProductDetailsComponent } from './app/component/product-details/product-details.component';
import { OrderConfirmComponent } from './app/component/order-confirm/order-confirm.component';

import { OrderDetailComponent } from './app/component/order-detail/order-detail.component';
import { AppComponent } from './app/app/app.component';


@NgModule({
  imports: [
    AppModule,
    ServerModule,
  ],
  bootstrap: [AppComponent],
})
export class AppServerModule {}
