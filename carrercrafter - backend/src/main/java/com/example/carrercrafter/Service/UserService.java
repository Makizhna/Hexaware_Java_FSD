package com.example.carrercrafter.Service;

import java.util.List;

import com.example.carrercrafter.dto.UserDto;
import com.example.carrercrafter.entities.User;

public interface UserService {
	
	UserDto saveUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getUserById(int id);
    UserDto updateUser(int id, UserDto updatedUserDto);
    UserDto login(String email, String password);
    boolean deleteUserAndRelated(int id);
    
    
   
    

}
