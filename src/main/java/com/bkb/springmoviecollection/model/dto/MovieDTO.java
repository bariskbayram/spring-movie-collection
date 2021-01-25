package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Movie;
import lombok.Data;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MovieDTO {

  private int movieId;
  private String title;
  private int releaseYear;
  private List<GenreDTO> genres = new ArrayList<>();
  private String description;
  private String mediaPath;

  public MovieDTO() {
  }

  public MovieDTO(String title, int releaseYear, String description, String mediaPath) {
    this.title = title;
    this.releaseYear = releaseYear;
    this.description = description;
    this.mediaPath = mediaPath;
  }

  @Transactional
  public static MovieDTO from(Movie movie) {
    MovieDTO movieDTO = new MovieDTO();
    movieDTO.setMovieId(movie.getMovieId());
    movieDTO.setTitle(movie.getTitle());
    movieDTO.setReleaseYear(movie.getReleaseYear());
    if(movie.getGenres().size() != 0){
      movieDTO.setGenres(movie.getGenres().stream()
          .map(GenreDTO::from).collect(Collectors.toList()));
    }
    movieDTO.setDescription(movie.getDescription());
    movieDTO.setMediaPath(movie.getMediaPath());
    return movieDTO;
  }
}
