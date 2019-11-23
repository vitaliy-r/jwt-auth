package com.epam;

import com.epam.model.Role;
import com.epam.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JwtAuthApp {

  public static void main(String[] args) {
    SpringApplication.run(JwtAuthApp.class, args);
  }

  @Bean
  public CommandLineRunner demoData(RoleRepository roleRepository) {
    return args -> {
      roleRepository.deleteAll();
      roleRepository.save(new Role("ROLE_USER"));
      roleRepository.save(new Role("ROLE_ADMIN"));
    };
  }

}