package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Genre;
import lombok.Data;


@Data
public class GenreDTO {

  private int genreId;
  private String name;

  public static GenreDTO from(Genre genre) {
    GenreDTO genreDTO = new GenreDTO();
    genreDTO.setGenreId(genre.getGenreId());
    genreDTO.setName(genre.getGenreName());
    return genreDTO;
  }

}
