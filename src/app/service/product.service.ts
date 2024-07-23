import { Injectable } from "@angular/core";
import { HttpClient,HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { env } from "../../envs/env";
import { Product } from "../models/product";
@Injectable({
    providedIn:'root',
})
export class ProductService{
    private apiGetProducts= env.apiBaseUrl+"/products";
    constructor(private http:HttpClient){
    }
    getProducts(
        keyword:string,
        category_id:number,
        page:number,
        limit:number
    )
    :Observable<Product[]>{
        const params= new HttpParams()
        .set('keyword',keyword)
        .set('category_id',category_id)
        .set('page',page.toString())
        .set('limit',limit.toString());
        return this.http.get<Product[]>(this.apiGetProducts,{params})
    }
    getDetailProduct(productId: number) {
        return this.http.get(`${env.apiBaseUrl}/products/${productId}`);
      }
    getProductByIds(productIds:number[]):Observable<Product[]>{
        debugger
        const params= new HttpParams().set('ids',productIds.join(','));
        return this.http.get<Product[]>(`${this.apiGetProducts}/by-ids`,{params});
    }
}