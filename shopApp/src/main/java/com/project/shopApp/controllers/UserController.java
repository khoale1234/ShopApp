package com.project.shopApp.controllers;

import com.project.shopApp.dtos.UserDTO;
import com.project.shopApp.dtos.UserLoginDTO;
import com.project.shopApp.models.User;
import com.project.shopApp.response.LoginResponse;
import com.project.shopApp.response.RegisterResponse;
import com.project.shopApp.response.UserResponse;
import com.project.shopApp.services.UserService;
import com.project.shopApp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.project.shopApp.components.LocalizationUtils;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;
    private final LocalizationUtils localizationUtils;
    @PostMapping("/register")
        public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody UserDTO userDTO,
                                                           BindingResult result) {
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                registerResponse.setMessage(errorMessages.toString());
                return ResponseEntity.badRequest().body(registerResponse);
            }
            if (!Objects.equals(userDTO.getPassword(), userDTO.getRetypePassword())){
                registerResponse.setMessage(localizationUtils.getLocalizeMessage(MessageKeys.PASSWORD_NOT_MATCH));
            }
            User user= userService.createUser(userDTO);
            registerResponse.setMessage(localizationUtils.getLocalizeMessage(MessageKeys.REGISTER_SUCCESSFULLY));
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }
    @PostMapping("/details")
    public  ResponseEntity<UserResponse> getUserDetails(@RequestHeader("Authorization") String token){
        try{
            String extracteToken= token.substring(7);
            User user= userService.getUserDetailsFromToken(extracteToken);
            return ResponseEntity.ok(UserResponse.fromUser(user));
        }
        catch (Exception e){
            return ResponseEntity.badRequest()
                    .build();        }
    }

    @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginDTO userLoginDTO
                                                   ){
//        Kiem tra thong tin dang nhap va tao token
        try {
            String token= userService.login(userLoginDTO.getPhoneNumber(),userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId()==null?1:userLoginDTO.getRoleId());
            //        Tra ve token trong response
            return ResponseEntity.ok(LoginResponse.builder()
                            .message(localizationUtils.getLocalizeMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                            .token(token)
                    .build());
        } catch (Exception  e) {
            return ResponseEntity.badRequest().body(LoginResponse.builder()
                    .message(localizationUtils.getLocalizeMessage(MessageKeys.LOGIN_FAILED,e.getMessage()))
                    .build());
        }


    }
}
