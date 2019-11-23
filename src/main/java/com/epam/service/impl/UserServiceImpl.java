package com.epam.service.impl;

import com.epam.model.Role;
import com.epam.model.User;
import com.epam.model.payload.JwtResponse;
import com.epam.model.payload.SignInRequest;
import com.epam.repository.RoleRepository;
import com.epam.repository.UserRepository;
import com.epam.security.JwtTokenProvider;
import com.epam.service.UserService;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final JwtTokenProvider tokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Override
  public User findById(String id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User is not found!"));
  }

  @Override
  public JwtResponse authenticateUser(SignInRequest signInRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            signInRequest.getUsernameOrEmail(),
            signInRequest.getPassword()
        ));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.generateToken(authentication);

    return new JwtResponse(jwt);
  }

  @Override
  public User createUser(User user) {
    Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() ->
        new RuntimeException("Role is not found!"));

    user.setRoles(Collections.singleton(role));
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
        .orElseThrow(() -> new UsernameNotFoundException(
            "User not found with username or email: " + usernameOrEmail));
  }

}