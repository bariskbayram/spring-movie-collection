package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.LanguageDto;
import com.bkb.springmoviecollection.model.entity.Language;
import com.bkb.springmoviecollection.service.LanguageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/languages")
public class LanguageController {

  private final LanguageService languageService;

  public LanguageController(LanguageService languageService) {
    this.languageService = languageService;
  }

  @GetMapping("get_all_languages")
  public List<LanguageDto> getAllLanguages() {
    List<Language> languages = languageService.getAllLanguages();
    List<LanguageDto> languageDtos =
        languages.stream().map(LanguageDto::from).collect(Collectors.toList());
    return languageDtos;
  }

  @GetMapping("get_by_id/{id}")
  public LanguageDto getLanguageById(@RequestParam("id") int languageId) {
    Language language = languageService.getLanguageById(languageId);
    return LanguageDto.from(language);
  }

  @GetMapping("get_by_movie_id/{id}")
  public List<LanguageDto> getLanguageByMovieId(@RequestParam("id") int movieId) {
    List<Language> languages = languageService.getLanguageByMovieId(movieId);
    List<LanguageDto> languageDtos = languages.stream()
        .map(LanguageDto::from).collect(Collectors.toList());
    return languageDtos;
  }

  @PostMapping("add_language")
  public String addLanguage(LanguageDto languageDTO) {
    languageService.addLanguage(Language.from(languageDTO));
    return "redirect:/movies/display_add_movie";
  }

  @DeleteMapping("delete_language_by_id/{id}")
  public void deleteLanguageById(@RequestParam("id") int languageId) {
    languageService.deleteLanguageById(languageId);
  }

}
