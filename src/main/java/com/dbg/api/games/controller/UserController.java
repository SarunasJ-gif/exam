package com.dbg.api.games.controller;

import com.dbg.api.games.dto.ErrorDto;
import com.dbg.api.games.dto.UserDto;
import com.dbg.api.games.dto.UserRegister;
import com.dbg.api.games.entity.User;
import com.dbg.api.games.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

  @Autowired
  private GameService gameService;

  @PostMapping("/register")
  public ResponseEntity<?> saveUser(@RequestBody UserRegister user) {
    gameService.addColumnPassword();
    Optional<User> optionalUser = Optional.ofNullable(gameService.getUserByName(user.getUsername()));
    if (optionalUser.isPresent()) {
      ErrorDto error = new ErrorDto("User already exists");
      return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
    UserDto savedUser = gameService.saveUser(user);
    return new ResponseEntity<>(savedUser, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UserRegister userToLogin) {
    Optional<User> optionalUser = Optional.ofNullable(gameService.getUserByName(userToLogin.getUsername()));
    if (optionalUser.isPresent()) {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      if (passwordEncoder.matches(userToLogin.getPassword(), optionalUser.get().getPassword())) {
        return new ResponseEntity<>("You pass exam to Devridge! Congratulations!", HttpStatus.OK);
      }
      ErrorDto wrongPassword = new ErrorDto("Wrong password");
      return new ResponseEntity<>(wrongPassword, HttpStatus.FORBIDDEN);
    }
    ErrorDto noSuchUser = new ErrorDto("No such user exists");
    return new ResponseEntity<>(noSuchUser, HttpStatus.NOT_FOUND);
  }
}

