import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { env } from "../../envs/env";
import { Observable } from "rxjs";
import { OrderDTO } from "../dtos/order/order.dto";

@Injectable({
    providedIn: 'root',
  })
  export class OrderService {
    private apiUrl = `${env.apiBaseUrl}/orders`;
  
    constructor(private http: HttpClient) {}
  
    placeOrder(orderData: OrderDTO): Observable<any> {    
      // Gửi yêu cầu đặt hàng
      return this.http.post(this.apiUrl, orderData);
    }
    getOrderById(orderId: number): Observable<any> {
      const url = `${env.apiBaseUrl}/orders/${orderId}`;
      return this.http.get(url);
    }
  
  }