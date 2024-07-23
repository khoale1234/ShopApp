import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './app/component/home/home.component';
import { LoginComponent } from './app/component/login/login.component';
import { RegisterComponent } from './app/component/register/register.component';
import { ProductDetailsComponent } from './app/component/product-details/product-details.component';
import { OrderConfirmComponent } from './app/component/order-confirm/order-confirm.component';
import { OrderDetailComponent } from './app/component/order-detail/order-detail.component';
import { OrderComponent } from './app/component/order/order.component';
import { AuthGuardFn } from './app/guards/auth.guard';
import { UserProfileComponent } from './app/component/user-profile/user-profile.component';
import { AdminComponent } from './app/admin/admin.component';
import { AdminGuardFn } from './app/guards/admin.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'Admin', component: AdminComponent, canActivate:[AdminGuardFn] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'products/:id', component: ProductDetailsComponent },
  { path: 'orders', component: OrderComponent,canActivate:[AuthGuardFn] },
  { path: 'user-profile', component: UserProfileComponent,canActivate:[AuthGuardFn] },
  { path: 'orders/:id', component: OrderDetailComponent },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
