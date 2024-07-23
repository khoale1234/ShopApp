import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { LoginComponent } from './app/component/login/login.component';
import { FooterComponent } from './app/component/footer/footer.component';
import { HeaderComponent } from './app/component/header/header.component';
import { OrderConfirmComponent } from './app/component/order-confirm/order-confirm.component';
import { RegisterComponent } from './app/component/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {  provideHttpClient, withFetch, withInterceptorsFromDi } from '@angular/common/http';
import { ProductDetailsComponent } from './app/component/product-details/product-details.component';
import { HTTP_INTERCEPTORS,HttpClientModule } from '@angular/common/http';
import { TokenInterceptor } from './app/interceptor/token.interceptor';
import { AppRoutingModule } from './app-routing.module';
import { OrderDetailComponent } from './app/component/order-detail/order-detail.component';
import { AppComponent } from './app/app/app.component';
import { HomeComponent } from './app/component/home/home.component';
import { OrderComponent } from './app/component/order/order.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserProfileComponent } from './app/component/user-profile/user-profile.component';
import { AdminComponent } from './app/admin/admin.component';

@NgModule({
  declarations: [
    HomeComponent,
    LoginComponent,
    FooterComponent,
    HeaderComponent,
    OrderConfirmComponent,
    RegisterComponent,
    ProductDetailsComponent,      
    OrderDetailComponent, AppComponent, OrderComponent, UserProfileComponent, AdminComponent    
  ],
  imports: [
    
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgbModule,
    
  ],
  providers: [
    provideClientHydration(), provideHttpClient(withFetch()),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
  ],
  bootstrap: [
    // ProductDetailsComponent
    // HomeComponent
    // RegisterComponent,
    // LoginComponent
    // OrderConfirmComponent
    // OrderDetailComponent
    AppComponent
  ]
})
export class AppModule { }
