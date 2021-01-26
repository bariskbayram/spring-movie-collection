package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Genre;
import lombok.Data;

@Data
public class GenreDto {

  private int genreId;
  private String genreName;

  public GenreDto() {
  }

  public GenreDto(int genreId) {
    this.genreId = genreId;
  }

  public static GenreDto from(Genre genre) {
    GenreDto genreDTO = new GenreDto();
    genreDTO.setGenreId(genre.getGenreId());
    genreDTO.setGenreName(genre.getGenreName());
    return genreDTO;
  }

}
