package com.bkb.springmoviecollection.model.exception;

public class TNotFoundException extends RuntimeException {

  public TNotFoundException(int id, Class<?> c) {
    super(String.format("%s belonging to this id %s was not found!", new Object[]{c.getName(), id}) );
  }

}
