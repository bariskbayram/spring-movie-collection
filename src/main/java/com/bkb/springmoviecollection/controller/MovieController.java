package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.MovieDTO;
import com.bkb.springmoviecollection.model.entity.Genre;
import com.bkb.springmoviecollection.model.entity.Movie;
import com.bkb.springmoviecollection.service.GenreService;
import com.bkb.springmoviecollection.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

  private final MovieService movieService;
  private final GenreService genreService;

  public MovieController(MovieService movieService, GenreService genreService) {
    this.movieService = movieService;
    this.genreService = genreService;
  }

  @GetMapping("get_all_movies")
  public List<MovieDTO> getAllMovies() {
    List<Movie> movies = movieService.getAllMovies();
    List<MovieDTO> movieDTOS = movies.stream().map(MovieDTO::from)
        .collect(Collectors.toList());
    return movieDTOS;
  }

  @GetMapping("get_by_id/{id}")
  public MovieDTO getMovieById(@RequestParam("id") int movieId) {
    Movie movie = movieService.getMovieById(movieId);
    return MovieDTO.from(movie);
  }

  @PostMapping("add_movie")
  public MovieDTO addMovie(@RequestBody MovieDTO movieDTO) {
    List<Genre> genres = movieDTO.getGenres().stream()
        .map(genreDTO -> genreService.getGenreById(genreDTO.getGenreId()))
        .collect(Collectors.toList());
    Movie movie = movieService.addMovie(Movie.from(movieDTO), genres);
    return MovieDTO.from(movie);
  }

}
