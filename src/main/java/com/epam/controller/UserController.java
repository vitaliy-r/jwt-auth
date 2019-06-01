package com.epam.controller;

import com.epam.model.User;
import com.epam.security.CurrentUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @GetMapping("/me")
//  @PreAuthorize("hasRole('USER')")
  public User getCurrentUser(@CurrentUser User currentUser) {
    return currentUser;
  }

}
