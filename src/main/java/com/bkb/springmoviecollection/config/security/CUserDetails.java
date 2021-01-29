package com.bkb.springmoviecollection.config.security;

import com.bkb.springmoviecollection.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CUserDetails implements UserDetails {

  private String username;
  private String password;
  private Set<? extends GrantedAuthority> grantedAuthorities;

  public static CUserDetails from(User user) {
    CUserDetails cUserDetails = new CUserDetails();
    cUserDetails.username = user.getUsername();
    cUserDetails.password = user.getPassword();
    if (user.getUserRole().equals("ADMIN")) {
      cUserDetails.grantedAuthorities = UserRole.ADMIN.getGrantedAuthorities();
    }else {
      cUserDetails.grantedAuthorities = UserRole.USER.getGrantedAuthorities();
    }
    return cUserDetails;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
