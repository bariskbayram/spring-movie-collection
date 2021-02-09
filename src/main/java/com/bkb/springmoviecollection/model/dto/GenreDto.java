package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Genre;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GenreDto {

  private int genreId;
  private String genreName;

  public static GenreDto from(Genre genre) {
    GenreDto genreDTO = new GenreDto();
    genreDTO.setGenreId(genre.getGenreId());
    genreDTO.setGenreName(genre.getGenreName());
    return genreDTO;
  }

  public static List<GenreDto> from(List<Genre> genres) {
    return genres.stream()
            .map(GenreDto::from)
            .collect(Collectors.toList());
  }

}
