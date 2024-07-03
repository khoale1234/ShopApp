package com.project.shopApp.services;

import com.project.shopApp.dtos.UserDTO;
import com.project.shopApp.exceptions.DataNotFoundException;
import com.project.shopApp.models.User;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phone, String password,Long role_id) throws Exception;
    User  getUserDetailsFromToken( String token) throws Exception;
}
