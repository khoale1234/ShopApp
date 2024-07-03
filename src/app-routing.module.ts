import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './app/component/home/home.component';
import { LoginComponent } from './app/component/login/login.component';
import { RegisterComponent } from './app/component/register/register.component';
import { ProductDetailsComponent } from './app/component/product-details/product-details.component';
import { OrderConfirmComponent } from './app/component/order-confirm/order-confirm.component';
import { OrderDetailComponent } from './app/component/order-detail/order-detail.component';

const routes: Routes = [
  {path: '',component: HomeComponent},
  {path:'login',component:LoginComponent},
  {path:'register',component:RegisterComponent},
  {path:'products/:id',component:ProductDetailsComponent},
  {path:'orders',component:OrderConfirmComponent},
  {path:'orders/id',component:OrderDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
