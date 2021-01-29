package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.UserDto;
import com.bkb.springmoviecollection.model.entity.User;
import com.bkb.springmoviecollection.service.CUserDetailsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

  private final CUserDetailsService cUserDetailsService;

  public UserController(CUserDetailsService cUserDetailsService) {
    this.cUserDetailsService = cUserDetailsService;
  }

  @GetMapping("register")
  public String displayRegister(Model model) {
    model.addAttribute("userDto", new UserDto());
    return "user/register";
  }

  @PostMapping("register")
  public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                             BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "user/register";
    }
    return cUserDetailsService.registerUser(userDto, bindingResult);
  }

  @GetMapping("display_admin_panel")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String displayAdminPanel(Model model) {
    List<User> userList = cUserDetailsService.getAllUsers();
    List<UserDto> userDtos = userList.stream().map(UserDto::from)
        .collect(Collectors.toList());
    model.addAttribute("userList", userDtos);
    return "user/adminPage";
  }

  @RequestMapping(value = "setAdmin/", params = "id")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String setAdminPermissions(@RequestParam("id") int id) {
    cUserDetailsService.updateUserPermission(id, "ADMIN");
    return "redirect:/users/display_admin_panel";
  }

  @RequestMapping(value = "setUser/", params = "id")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String setUserPermissions(@RequestParam("id") int id) {
    cUserDetailsService.updateUserPermission(id, "USER");
    return "redirect:/users/display_admin_panel";
  }

}
