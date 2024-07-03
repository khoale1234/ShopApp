import { Component, OnInit, ViewChild } from '@angular/core';
import { LoginDTO } from '../../../dtos/user/login.dto';
import { UserService } from '../../../service/user.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { LoginResponse } from '../../response/user/login.response';
import { TokenService } from '../../../service/token.service';
import { RoleService } from '../../../service/role.service';
import { Role } from '../../models/role';
import { UserResponse } from '../../response/user/user.response';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  @ViewChild('LoginForm') LoginForm!: NgForm;
  phone:string='0877202988';
  password:string='Kkhongbiet!@#1';
  roles: Role[] = []; // Mảng roles
  rememberMe: boolean = true;
  selectedRole: Role | undefined; // Biến để lưu giá trị được chọn từ dropdown
  userResponse? : UserResponse
  constructor( 
    private router: Router,
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService
  ){

  }
  onPhoneChange(){
    console.log("Phone typed: "+this.phone)
  }
  ngOnInit() {
    // Gọi API lấy danh sách roles và lưu vào biến roles
    debugger
    this.roleService.getRoles().subscribe({      
      next: (roles: Role[]) => { // Sử dụng kiểu Role[]
        debugger
        this.roles = roles;
        this.selectedRole = roles.length > 0 ? roles[0] : undefined;
      },
      complete: () => {
        debugger
      },  
      error: (error: any) => {
        debugger
        console.error('Error getting roles:', error);
      }
    });
  }
  login() {
    const message = `phone: ${this.phone}` +
      `password: ${this.password}`;
    //alert(message);
    debugger

    const loginDTO: LoginDTO = {
      phone_number: this.phone,
      password: this.password,
      role_id: this.selectedRole?.id ?? 1
    };
    this.userService.login(loginDTO).subscribe({
      next: (response: LoginResponse) => {
        debugger;
        const { token } = response;
        if (this.rememberMe) {
          this.tokenService.setToken(token);
          debugger
          this.userService.getUserDetails(token).subscribe({
            next:(response: any)=>{
              debugger
               this.userResponse={
                ...response,
                date_of_birth:new Date(response.date_of_birth),
                // id:response.id,
                // fullname:response.fullname,
                // address:response.address,
                // is_active:response.is_active,
                // date_of_birth:new Date(response.date_of_birth),
                // facebook_account_id:response.date_of_birth,
                // google_account_id:response.google_account_id,
                // role:response.role,
               }
               this.userService.saveUserResponseToLocalStorage(this.userResponse);
               this.router.navigate(['/']);
            },
            complete: () => {
              debugger;
            },
            error: (error: any) => {
              debugger;
              alert(error.error.message);
            }
          })
        }                
       
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        alert(error.error.message);
      }
    });
  }
}

