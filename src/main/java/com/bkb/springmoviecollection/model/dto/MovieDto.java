package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Movie;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MovieDto {

  private int movieId;
  private String title;
  private int releaseYear;
  private String description;
  private String mediaPath;
  private List<Integer> selectedGenreIdList;
  private List<Integer> selectedLanguageIdList;
  private List<GenreDto> genres = new ArrayList<>();
  private List<LanguageDto> languages = new ArrayList<>();

  public MovieDto() {
  }

  public static MovieDto from(Movie movie) {
    MovieDto movieDTO = new MovieDto();
    movieDTO.setMovieId(movie.getMovieId());
    movieDTO.setTitle(movie.getTitle());
    movieDTO.setReleaseYear(movie.getReleaseYear());
    movieDTO.setDescription(movie.getDescription());
    movieDTO.setMediaPath(movie.getMediaPath());
    return movieDTO;
  }
}
