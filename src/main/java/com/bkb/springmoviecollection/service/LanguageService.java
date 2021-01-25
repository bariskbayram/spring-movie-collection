package com.bkb.springmoviecollection.service;

import com.bkb.springmoviecollection.model.entity.Language;
import com.bkb.springmoviecollection.model.exception.TNotFoundException;
import com.bkb.springmoviecollection.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

  private final LanguageRepository languageRepository;

  public LanguageService(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
  }

  public List<Language> getAllLanguages() {
    return languageRepository.findAll();
  }

  public Language getLanguageById(int languageId) {
    return languageRepository.findById(languageId).orElseThrow( () ->
        new TNotFoundException(languageId, Language.class));
  }

  public List<Language> getLanguageByMovieId(int movieId) {
    return languageRepository.findAllByMoviesMovieId(movieId);
  }

  public Language addLanguage(Language language) {
    return languageRepository.save(language);
  }

  public void deleteLanguageById(int id) {
    languageRepository.deleteById(id);
  }
}
