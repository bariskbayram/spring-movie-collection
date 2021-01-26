package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Language;
import lombok.Data;

@Data
public class LanguageDto {

  private int languageId;
  private String languageName;

  public LanguageDto() {
  }

  public LanguageDto(int languageId, String languageName) {
    this.languageId = languageId;
    this.languageName = languageName;
  }

  public static LanguageDto from(Language language) {
    LanguageDto languageDTO = new LanguageDto();
    languageDTO.setLanguageId(language.getLanguageId());
    languageDTO.setLanguageName(language.getLanguageName());
    return languageDTO;
  }

}
