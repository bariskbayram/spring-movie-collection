package com.bkb.springmoviecollection.model.entity;

import com.bkb.springmoviecollection.model.dto.LanguageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "languages")
public class Language {

  @Id
  @SequenceGenerator(name = "language_sequence", sequenceName = "language_sequence",
      allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "language_sequence"
  )
  @Column(name = "language_id", updatable = false)
  private int languageId;

  @Column(name = "language_name", nullable = false)
  private String languageName;

  @ManyToMany(mappedBy = "languages")
  private List<Movie> movies = new ArrayList<>();

  public Language(String languageName) {
    this.languageName = languageName;
  }

  public static Language from(LanguageDto languageDTO) {
    Language language = new Language();
    language.setLanguageName(languageDTO.getLanguageName());
    return language;
  }

  public static List<Language> from(List<LanguageDto> languageDtos) {
    return languageDtos.stream()
            .map(Language::from)
            .collect(Collectors.toList());
  }

  @PreRemove
  public void removeMovies() {
    movies.forEach(movie -> movie.removeLanguage(this));
  }

  @Override
  public String toString() {
    return "Genre{" +
        "id=" + languageId +
        ", name='" + languageName + '\'' +
        '}';
  }
}
