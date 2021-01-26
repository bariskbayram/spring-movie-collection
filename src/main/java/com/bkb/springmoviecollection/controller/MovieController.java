package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.GenreDto;
import com.bkb.springmoviecollection.model.dto.LanguageDto;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
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
  public String getAllMovies(@ModelAttribute MoviePage moviePage, Model model) {
    Page<Movie> movies = movieService.getAllMovies(moviePage);
    List<MovieDto> movieDtos = movies.stream().map(MovieDto::from)
        .collect(Collectors.toList());
    model.addAttribute("movieList", movieDtos);
    return "movie/movieList";
  }

  @GetMapping("get_by_id/{id}")
  public MovieDto getMovieById(@RequestParam("id") int movieId) {
    Movie movie = movieService.getMovieById(movieId);
    return MovieDto.from(movie);
  }

  @GetMapping("get_movies_search_by")
  public String getMoviesSearchTitle(
      MovieSpecification movieSpecification, @ModelAttribute MoviePage moviePage, Model model) {

    Page<Movie> movies = movieService.searchBy(movieSpecification, moviePage);
    List<MovieDto> movieDtos = movies.stream().map(MovieDto::from)
        .collect(Collectors.toList());

    model.addAttribute("movieList", movieDtos);
    return "movie/movieList";
  }

  @GetMapping("display_add_movie")
  public String getAddMoviePage(Model model) {
    List<Genre> genres = genreService.getAllGenres();
    List<GenreDto> genreDtos = genres.stream().map(GenreDto::from)
        .collect(Collectors.toList());
    List<Language> languages = languageService.getAllLanguages();
    List<LanguageDto> languageDtos = languages.stream().map(LanguageDto::from)
        .collect(Collectors.toList());
    model.addAttribute("genreList", genreDtos);
    model.addAttribute("languageList", languageDtos);
    model.addAttribute("movieDTO", new MovieDto());
    return "movie/addMovie";
  }

  @PostMapping("add_movie")
  public String addMovie(@ModelAttribute MovieDto movieDTO) {
    List<Genre> genres = movieDTO.getSelectedGenreIdList().stream()
        .map(genreId -> genreService.getGenreById(genreId))
        .collect(Collectors.toList());

    List<Language> languages = movieDTO.getSelectedLanguageIdList().stream()
        .map(languageId -> languageService.getLanguageById(languageId))
        .collect(Collectors.toList());

    movieService.addMovie(Movie.from(movieDTO), genres, languages);

    return "redirect:/movies/get_all_movies";
  }

}
