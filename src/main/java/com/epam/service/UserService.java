package com.epam.service;

import com.epam.model.User;
import com.epam.model.payload.JwtResponse;
import com.epam.model.payload.SignInRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  User findById(String id);

  JwtResponse authenticateUser(SignInRequest signInRequest);

  User createUser(User user);

}
