import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { TokenService } from '../service/token.service';
import { Router } from 'express';
import { UserResponse } from '../response/user/user.response';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent implements OnInit {
  userResponse?:UserResponse | null;
  constructor(
    private userService: UserService,       
    private tokenService: TokenService,    
    private router: Router,
  ) {
    
   }
  ngOnInit() {
    this.userResponse = this.userService.getUserResponseFromLocalStorage();    
   }  
  logout() {
    this.userService.removeUserFromLocalStorage();
    this.tokenService.removeToken();
    this.userResponse = this.userService.getUserResponseFromLocalStorage();    
  }
}
