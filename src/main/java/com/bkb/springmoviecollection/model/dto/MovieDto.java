package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Movie;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MovieDto {

  private int movieId;
  private String title;
  private int releaseYear;
  private String description;
  private String mediaPath;
  private MultipartFile multipartFile;
  private List<Integer> selectedGenreIdList = new ArrayList<>();
  private List<Integer> selectedLanguageIdList = new ArrayList<>();
  private List<PerformerDto> selectedPerformerDtoList = new ArrayList<>();

  public MovieDto() {
  }

  public String getPhotosImagePath() {
    if(mediaPath == null || movieId == 0 ) {
      return null;
    }
    return "/movie-photos/" + movieId + "/" + mediaPath;
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

  public static List<MovieDto> from(List<Movie> movies) {
    return movies.stream()
            .map(MovieDto::from)
            .collect(Collectors.toList());
  }
}
