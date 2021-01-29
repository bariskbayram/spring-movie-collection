package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {

  @NotNull
  @Size(min = 2, max = 30, message = "Fullname length must be min 2 and max 30.")
  private String fullname;
  @NotNull
  @Size(min = 5, max = 20, message = "Username length must be min 5 and max 20.")
  private String username;
  @NotNull
  @Size(min = 5, max = 255, message = "Password length must be more complicate.")
  private String password;

  private int userId;
  private String userRole;

  public static UserDto from(User user) {
    UserDto userDto = new UserDto();
    userDto.setUserId(user.getUserId());
    userDto.setFullname(user.getFullname());
    userDto.setUsername(user.getUsername());
    userDto.setUserRole(user.getUserRole());
    return userDto;
  }

  @Override
  public String toString() {
    return "UserDto{" +
        "fullname='" + fullname + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
