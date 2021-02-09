package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.LanguageDto;
import com.bkb.springmoviecollection.model.entity.Language;
import com.bkb.springmoviecollection.service.LanguageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/languages")
public class LanguageController {

  private final LanguageService languageService;

  public LanguageController(LanguageService languageService) {
    this.languageService = languageService;
  }

  @GetMapping("get_all_languages")
  @PreAuthorize("hasAuthority('data:read')")
  public List<LanguageDto> getAllLanguages() {
    List<Language> languages = languageService.getAllLanguages();
    return LanguageDto.from(languages);
  }

  @GetMapping("get_by_id/{id}")
  @PreAuthorize("hasAuthority('data:read')")
  public LanguageDto getLanguageById(@RequestParam("id") int languageId) {
    Language language = languageService.getLanguageById(languageId);
    return LanguageDto.from(language);
  }

  @GetMapping("get_by_movie_id/{id}")
  @PreAuthorize("hasAuthority('data:read')")
  public List<LanguageDto> getLanguageByMovieId(@RequestParam("id") int movieId) {
    List<Language> languages = languageService.getLanguageByMovieId(movieId);
    return LanguageDto.from(languages);
  }

  @PostMapping("add_language")
  @PreAuthorize("hasAuthority('data:insert')")
  public String addLanguage(LanguageDto languageDTO) {
    languageService.addLanguage(Language.from(languageDTO));
    return "redirect:/movies/display_add_movie";
  }

  @DeleteMapping("delete_language_by_id/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void deleteLanguageById(@RequestParam("id") int languageId) {
    languageService.deleteLanguageById(languageId);
  }

}
