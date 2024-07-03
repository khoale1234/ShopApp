import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Router } from '@angular/router';
import { UserService } from '../../../service/user.service';
import { RegisterDTO } from '../../../dtos/user/register.dto';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  phone:string;
  password:string;
  retypePassword:string;
  fullName:string;
  address:string
  isAccepted:boolean;
  dateOfBirth:Date;
  constructor( private router: Router,private userService: UserService){
    this.phone='';
    this.password='';
    this.retypePassword='';
    this.fullName='';
    this.address='';
    this.isAccepted=true;
    this.dateOfBirth=new Date;
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear()-18)

  }
  onPhoneChange(){
    console.log("Phone typed: "+this.phone)
  }
  register(){
    const message="phone: "+this.phone+
        "password: "+this.password+
        "retypedPassword: "+this.retypePassword+
        "address: "+this.address+
        "fullName: "+this.fullName+
        "isAccepted: "+this.isAccepted+
        "dateOfBirth: "+this.dateOfBirth;

    

    const registerDTO: RegisterDTO=
    {
        "fullname":this.fullName,
        "phone_number":this.phone,
        "address" :this.address,
        "password": this.password,
        "retype_password":this.retypePassword,
        "date_of_birth":this.dateOfBirth,
        "facebook_account_id":0,
        "google_account_id":0,
        "role_id":1
    };
    this.userService.register(registerDTO).subscribe({
      next: (response:any) =>{
        debugger
        if(response && (response.status==200||response.status==201)){
          this.router.navigate(['/login']);
        }
        else{
          // xu li truong hop dang ki khong thanh cong
        }
      },
      complete: ()=>{
        debugger
      },
      error:(error: any)=>{
        debugger
       alert('Can not register, error: '+error.error)
      }

    }
);
  }
  checkPasswordsMatch(){
    if(this.password!=this.retypePassword){
      this.registerForm.form.controls['retypePassword'].setErrors({'passwordMismatch':true});
    }
    else{
      this.registerForm.form.controls['retypePassword'].setErrors(null)
    }
  }
  checkAge(){
    if(this.dateOfBirth){
      const today = new Date();
      const birthDate= new Date(this.dateOfBirth);
      let age= today.getFullYear()-birthDate.getFullYear();
      const monthDiff= today.getMonth()-birthDate.getMonth();
      if(monthDiff<0|| (monthDiff==0 && today.getDate()<birthDate.getDate()) ){
        age--;
      }
      if(age<18){
        this.registerForm.form.controls['dateOfBirth'].setErrors({'invalidDate':true})
      }
      else{
        this.registerForm.form.controls['dateOfBirth'].setErrors(null);
      }
    }
  }
}
