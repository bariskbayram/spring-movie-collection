package com.bkb.springmoviecollection.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    exposeDirectory(registry);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("user/login");
    registry.addRedirectViewController("/register", "/users/register");
    registry.addRedirectViewController("/", "/movies/get_all_movies");
  }

  //Exposing directory for saving movie medias
  private void exposeDirectory(ResourceHandlerRegistry registry) {
    Path uploadDir = Paths.get("movie-photos");
    String uploadPath = uploadDir.toFile().getAbsolutePath();
    registry.addResourceHandler("/movie-photos/**").addResourceLocations("file:./" + uploadPath + "/");
  }
}
