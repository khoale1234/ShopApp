import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Validator } from 'class-validator';
import { UserService } from '../../service/user.service';
import { TokenService } from '../../service/token.service';
import { UserResponse } from '../../response/user/user.response';
import { response } from 'express';
import { error } from 'console';
import { HttpErrorResponse } from '@angular/common/http';
import { UpdateUserDTO } from '../../dtos/user/update.user.dto';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent {
  token:string = '';
  userResponse?: UserResponse;
  userProfileForm!: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private activateRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private tokenService: TokenService
  ){
    this.userProfileForm= this.formBuilder.group({
      fullname:['',],
      address: ['',Validators.minLength(5)],
      password: ['',Validators.minLength(3)],
      retypePassword:['',Validators.minLength(3)],
      date_of_birth: [Date.now()],
    },{
      validators: this.passwordMatchValidator
    });
  }
  ngOnInit():void{
    debugger
    this.token=this.tokenService.getToken();
    this.userService.getUserDetails(this.token).subscribe({
      next:(response:any)=>{
        debugger
        this.userResponse={
          ...response,
          date_of_birth: new Date(response.date_of_birth)
        };
        this.userProfileForm.patchValue({
          fullname: this.userResponse?.fullname?? "",
          address: this.userResponse?.address ?? "",
          date_of_birth: this.userResponse?.date_of_birth.toISOString().substring(0,10)
        })
        this.userService.saveUserResponseToLocalStorage(this.userResponse);
        // this.router.navigate(['/'])
      },
      complete:()=>{
        debugger;
      },
      error: (error: any)=>{
        debugger
        alert(error.error.message);
      }
    })
  }
  passwordMatchValidator(): ValidatorFn {
    return (formGroup: AbstractControl): ValidationErrors | null => {
      const password = formGroup.get('password')?.value;
      const retypedPassword = formGroup.get('retype_password')?.value;
      if (password !== retypedPassword) {
        return { passwordMismatch: true };
      }
  
      return null;
    };
  }
  save(): void {
    debugger
    if (this.userProfileForm.valid) {
      const updateUserDTO: UpdateUserDTO = {
        fullname: this.userProfileForm.get('fullname')?.value,
        address: this.userProfileForm.get('address')?.value,
        password: this.userProfileForm.get('password')?.value,
        retype_password: this.userProfileForm.get('retype_password')?.value,
        date_of_birth: this.userProfileForm.get('date_of_birth')?.value
      };
  
      this.userService.updateUserDetail(this.token, updateUserDTO)
        .subscribe({
          next: (response: any) => {
            debugger
            alert("cập nhật thông tin thành công")
            this.userService.removeUserFromLocalStorage();
            this.tokenService.removeToken();
            this.router.navigate(['/login']);
          },
          error: (error: HttpErrorResponse) => {
            debugger;
            console.error(error?.message ?? '');
          } 
        });
    } else {
      if (this.userProfileForm.hasError('passwordMismatch')) {        
        console.error('Mật khẩu và mật khẩu gõ lại chưa chính xác')
      }
    }
  }    
}
