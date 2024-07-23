import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { register } from 'module';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/user/register.dto';
import { LoginDTO } from '../dtos/user/login.dto';
import { env } from '../../envs/env';
import { UserResponse } from '../response/user/user.response';
import { UpdateUserDTO } from '../dtos/user/update.user.dto';
import { ApiResponse } from '../response/api.response';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  private  apiRegister=env.apiBaseUrl+"/users/register";
  private  apiLogin=env.apiBaseUrl+"/users/login";
  private  apiUserDetails=env.apiBaseUrl+"/users/details"
  private  apiConfig={
    headers:this.createHeader()
  }
  constructor(private http: HttpClient) {}
  register(registerDTO: RegisterDTO): Observable<any>{
    return this.http.post(this.apiRegister,registerDTO,this.apiConfig)
  }
  login(loginDTO:LoginDTO):Observable<any>{
    return this.http.post(this.apiLogin,loginDTO,this.apiConfig)
  }
  private createHeader(): HttpHeaders{
    return new HttpHeaders({
      "Content-Type":"application/json",
      "Accept-Language":'vi'
    });
  }
  getUserDetails(token:string){
    return this.http.post(this.apiUserDetails,{
      'Content-type': 'application/json',
      Authorization:'Bearer '+token
    })
  }
  updateUserDetail(token: string, updateUserDTO: UpdateUserDTO): Observable<ApiResponse>  {
    debugger
    let userResponse = this.getUserResponseFromLocalStorage();        
    return this.http.put<ApiResponse>(`${this.apiUserDetails}/${userResponse?.id}`,updateUserDTO,{
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      })
    })
  }
  saveUserResponseToLocalStorage(userResponse?: UserResponse){
    try{
      debugger
      if(userResponse==null||!userResponse){
        return;
      }
      const userResponseJSON=JSON.stringify(userResponse);
      localStorage?.setItem('user',userResponseJSON);
      console.log('User response saved to local storage');
    }
    catch(error){
      console.log('Error saving user response to local storage',error);
    }
  }
  getUserResponseFromLocalStorage(){
    try{
      const userResponseJSON=localStorage?.getItem('user');
      if(userResponseJSON==null||userResponseJSON==undefined){
        return null;
      }
      const userResponse= JSON.parse(userResponseJSON!);
      console.log('User response retrieved from local storage.');
      return userResponse;
    }
    catch(error){
      console.log('Error retrieving user response from local storage',error);
    }
  }
  removeUserFromLocalStorage():void {
    try {
      // Remove the user data from local storage using the key
      localStorage.removeItem('user');
      console.log('User data removed from local storage.');
    } catch (error) {
      console.error('Error removing user data from local storage:', error);
      // Handle the error as needed
    }
  }
  } 

