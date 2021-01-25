package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.MovieDto;
import com.bkb.springmoviecollection.model.entity.Genre;
import com.bkb.springmoviecollection.model.entity.Language;
import com.bkb.springmoviecollection.model.entity.Movie;
import com.bkb.springmoviecollection.model.search.MoviePage;
import com.bkb.springmoviecollection.model.search.MovieSpecification;
import com.bkb.springmoviecollection.service.GenreService;
import com.bkb.springmoviecollection.service.LanguageService;
import com.bkb.springmoviecollection.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

  private final MovieService movieService;
  private final GenreService genreService;
  private final LanguageService languageService;

  public MovieController(MovieService movieService,
                         GenreService genreService,
                         LanguageService languageService) {

    this.movieService = movieService;
    this.genreService = genreService;
    this.languageService = languageService;
  }

  @GetMapping("get_all_movies")
  public List<MovieDto> getAllMovies(MoviePage moviePage) {
    Page<Movie> movies = movieService.getAllMovies(moviePage);
    List<MovieDto> movieDtos = movies.stream().map(MovieDto::from)
        .collect(Collectors.toList());
    return movieDtos;
  }

  @GetMapping("get_by_id/{id}")
  public MovieDto getMovieById(@RequestParam("id") int movieId) {
    Movie movie = movieService.getMovieById(movieId);
    return MovieDto.from(movie);
  }

  @GetMapping("get_movies_search_by")
  public List<MovieDto> getMoviesSearchTitle(
      MovieSpecification movieSpecification, MoviePage moviePage) {

    Page<Movie> movies = movieService.searchBy(movieSpecification, moviePage);
    List<MovieDto> movieDtos = movies.stream().map(MovieDto::from)
        .collect(Collectors.toList());
    return movieDtos;
  }

  @PostMapping("add_movie")
  public MovieDto addMovie(@RequestBody MovieDto movieDTO) {
    List<Genre> genres = movieDTO.getGenres().stream()
        .map(genreDTO -> genreService.getGenreById(genreDTO.getGenreId()))
        .collect(Collectors.toList());

    List<Language> languages = movieDTO.getLanguages().stream()
        .map(languageDTO -> languageService.getLanguageById(languageDTO.getLanguageId()))
        .collect(Collectors.toList());

    Movie movie = movieService.addMovie(
        Movie.from(movieDTO),
        genres,
        languages);

    return MovieDto.from(movie);
  }

}
