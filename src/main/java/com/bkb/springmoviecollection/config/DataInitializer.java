package com.bkb.springmoviecollection.config;

import com.bkb.springmoviecollection.model.entity.User;
import com.bkb.springmoviecollection.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public DataInitializer(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    User user = new User();
    user.setUsername("user");
    user.setFullname("default user");
    user.setPassword(passwordEncoder.encode("password"));
    user.setUserRole("USER");

    User admin = new User();
    admin.setUsername("admin");
    admin.setFullname("default admin");
    admin.setPassword(passwordEncoder.encode("password"));
    admin.setUserRole("ADMIN");

    userRepository.save(user);
    userRepository.save(admin);
  }
}
