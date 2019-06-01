package com.epam.controller;

import com.epam.model.User;
import com.epam.security.CurrentUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  @GetMapping("/me")
//  @PreAuthorize("hasRole('ADMIN')")
  public User getCurrentAdmin(@CurrentUser User currentAdmin) {
    return currentAdmin;
  }

}
