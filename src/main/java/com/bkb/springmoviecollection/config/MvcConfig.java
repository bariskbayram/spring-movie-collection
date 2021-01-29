package com.bkb.springmoviecollection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    exposeDirectory("movie-photos", registry);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("user/login");
    registry.addRedirectViewController("/register", "/users/register");
    registry.addRedirectViewController("/", "/movies/get_all_movies");
  }

  private void exposeDirectory(String dir, ResourceHandlerRegistry registry) {
    Path uploadDir = Paths.get(dir);
    String uploadPath = uploadDir.toFile().getAbsolutePath();

//    if (dir.startsWith("../")) dir = dir.replace("../", "");
//    registry.addResourceHandler("/" + dir + "/**").addResourceLocations("file:/"+ uploadPath + "/");

      registry.addResourceHandler("/movie-photos/**").addResourceLocations("file:/" + uploadPath + "/");
  }
}
