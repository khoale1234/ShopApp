import { Injectable } from "@angular/core";
import { HttpClient,HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { env } from "../app/envs/env";
import { Category } from "../app/models/category";

@Injectable({
    providedIn:'root',
})
export class CategoryService {
    private apiGetCategories  = `${env.apiBaseUrl}/categories`;
  
    constructor(private http: HttpClient) { }
    getCategories(page: number, limit: number):Observable<Category[]> {
      const params = new HttpParams()
        .set('page', page.toString())
        .set('limit', limit.toString());     
        return this.http.get<Category[]>(this.apiGetCategories, { params });           
    }
  }
  