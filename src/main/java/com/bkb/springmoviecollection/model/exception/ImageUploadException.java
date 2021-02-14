package com.bkb.springmoviecollection.model.exception;

import java.io.IOException;

public class ImageUploadException extends IOException {

  public ImageUploadException(String message) {
    super(String.format("Could not save media file for: %s", message) );
  }
}
