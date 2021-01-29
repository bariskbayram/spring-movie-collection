package com.bkb.springmoviecollection.service;

import com.bkb.springmoviecollection.config.security.CUserDetails;
import com.bkb.springmoviecollection.model.dto.UserDto;
import com.bkb.springmoviecollection.model.entity.User;
import com.bkb.springmoviecollection.model.exception.TNotFoundException;
import com.bkb.springmoviecollection.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class CUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public CUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow( () -> new TNotFoundException(username, User.class));

    return CUserDetails.from(user);
  }

  public String registerUser(UserDto userDto, BindingResult bindingResult) {
    Optional<User> userByUsername = userRepository.findByUsername(userDto.getUsername());

    if(userByUsername.isPresent()) {
      bindingResult.addError(new FieldError("userDto", "username", "Username is already exist."));
      return "user/register";
    }else {
      User user = new User();
      user.setFullname(userDto.getFullname());
      user.setUsername(userDto.getUsername());
      user.setPassword(passwordEncoder.encode(userDto.getPassword()));
      user.setUserRole("USER");
      userRepository.save(user);
      return "redirect:/movies/get_all_movies";
    }
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public void updateUserPermission(int id, String user) {
    userRepository.updateUserRole(id, user);
  }
}
