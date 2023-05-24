package com.dbg.api.games.mapper;

import com.dbg.api.games.entity.User;
import com.dbg.api.games.dto.UserDto;
import com.dbg.api.games.dto.UserRegister;

public class UserConverter {

  public static UserDto convertToUserDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    return userDto;
  }

  public static User convertToUser(UserDto userDto) {
    User user = new User();
    user.setUsername(userDto.getUsername());
    return user;
  }

  public static User convertToUserFromRegister(UserRegister userRegister) {
    User user = new User();
    user.setUsername(userRegister.getUsername());
    user.setPassword(userRegister.getPassword());
    return user;
  }
}
