package com.bkb.springmoviecollection.config.security;

public enum UserPermission {

  DATA_READ("data:read"),
  DATA_INSERT("data:insert"),
  DATA_UPDATE("data:update"),
  DATA_DELETE("data:delete");

  private final String permission;

  UserPermission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
