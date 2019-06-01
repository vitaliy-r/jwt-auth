package com.epam.controller;

import com.epam.model.User;
import com.epam.model.payload.JwtResponse;
import com.epam.model.payload.SignInRequest;
import com.epam.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  @PostMapping("/signin")
  public JwtResponse authenticateUser(@RequestBody SignInRequest request) {
    return userService.authenticateUser(request);
  }

  @PostMapping("/signup")
  public User registerUser(@Valid @RequestBody User user) {
    return userService.createUser(user);
  }

}