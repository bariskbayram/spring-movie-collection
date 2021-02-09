package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Language;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class LanguageDto {

  private int languageId;
  private String languageName;

  public static LanguageDto from(Language language) {
    LanguageDto languageDTO = new LanguageDto();
    languageDTO.setLanguageId(language.getLanguageId());
    languageDTO.setLanguageName(language.getLanguageName());
    return languageDTO;
  }

  public static List<LanguageDto> from(List<Language> languages) {
    return languages.stream()
            .map(LanguageDto::from)
            .collect(Collectors.toList());
  }

}
