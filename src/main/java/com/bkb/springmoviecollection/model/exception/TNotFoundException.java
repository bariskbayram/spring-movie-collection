package com.bkb.springmoviecollection.model.exception;

public class TNotFoundException extends RuntimeException {

  public <T> TNotFoundException(T t, Class<?> c) {
    super(String.format("%s belonging to this {id or username} %s was not found!", c.getName(), t) );
  }

}
