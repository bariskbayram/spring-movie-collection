package com.bkb.springmoviecollection.config.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {

  USER(Sets.newHashSet(
      UserPermission.DATA_READ,
      UserPermission.DATA_INSERT,
      UserPermission.DATA_UPDATE)),
  ADMIN(Sets.newHashSet(
      UserPermission.DATA_READ,
      UserPermission.DATA_INSERT,
      UserPermission.DATA_UPDATE,
      UserPermission.DATA_DELETE));

  private final Set<UserPermission> permissions;

  UserRole(Set<com.bkb.springmoviecollection.config.security.UserPermission> permissions) {
    this.permissions = permissions;
  }

  public Set<com.bkb.springmoviecollection.config.security.UserPermission> getPermissions() {
    return permissions;
  }

  public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
    Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toSet());
    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return permissions;
  }
}
