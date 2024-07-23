import { Injectable } from "@angular/core";
import { HttpClient,HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { env } from "../../envs/env";
@Injectable({
    providedIn:'root',
})
export class RoleService{
    private apiGetRoles= env.apiBaseUrl+"/roles";
    constructor(private http: HttpClient){}
    getRoles():Observable<any>{
        return this.http.get<any[]>(this.apiGetRoles)
    }
}