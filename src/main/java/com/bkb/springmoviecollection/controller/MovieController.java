package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.config.MediaUpload;
import com.bkb.springmoviecollection.model.dto.GenreDto;
import com.bkb.springmoviecollection.model.dto.LanguageDto;
import com.bkb.springmoviecollection.model.dto.MovieDto;
import com.bkb.springmoviecollection.model.dto.PerformerDto;
import com.bkb.springmoviecollection.model.entity.Genre;
import com.bkb.springmoviecollection.model.entity.Language;
import com.bkb.springmoviecollection.model.entity.Movie;
import com.bkb.springmoviecollection.model.entity.Performer;
import com.bkb.springmoviecollection.model.exception.ImageUploadException;
import com.bkb.springmoviecollection.model.search.MoviePage;
import com.bkb.springmoviecollection.model.search.MovieSpecification;
import com.bkb.springmoviecollection.service.*;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
public class MovieController {

  private final MovieService movieService;
  private final GenreService genreService;
  private final LanguageService languageService;
  private final PerformerService performerService;
  private final MoviePerformerService moviePerformerService;

  public MovieController(MovieService movieService,
                         GenreService genreService,
                         LanguageService languageService,
                         PerformerService performerService, MoviePerformerService moviePerformerService) {

    this.movieService = movieService;
    this.genreService = genreService;
    this.languageService = languageService;
    this.performerService = performerService;
    this.moviePerformerService = moviePerformerService;
  }

  @GetMapping("get_all_movies")
  @PreAuthorize("hasAuthority('data:read')")
  public String getAllMovies(MoviePage moviePage, Model model) {
    Page<Movie> movies = movieService.getAllMovies(moviePage);
    List<MovieDto> movieDtos = movies.stream().map(MovieDto::from)
        .collect(Collectors.toList());
    model.addAttribute("movieList", movieDtos);
    return "movie/movieList";
  }

  @GetMapping(value = "get_by_id/", params = "id")
  @PreAuthorize("hasAuthority('data:read')")
  public String getMovieById(@RequestParam("id") int movieId, Model model) {
    Movie movie = movieService.getMovieById(movieId);
    List<Genre> genres = genreService.getGenreByMovieId(movieId);
    List<GenreDto> genreDtos = genres.stream().map(GenreDto::from)
        .collect(Collectors.toList());
    List<Language> languages = languageService.getLanguageByMovieId(movieId);
    List<LanguageDto> languageDtos = languages.stream().map(LanguageDto::from)
        .collect(Collectors.toList());
    List<PerformerDto> performers = performerService.getPerformersByMovieId(movieId);

    model.addAttribute("movie", MovieDto.from(movie));
    model.addAttribute("genres", genreDtos);
    model.addAttribute("languages", languageDtos);
    model.addAttribute("performers", performers);
    return "movie/movieDetail";
  }

  @GetMapping("get_movies_search_by")
  @PreAuthorize("hasAuthority('data:read')")
  public String getMoviesSearchTitle(
      MovieSpecification movieSpecification, MoviePage moviePage, Model model) {

    Page<Movie> movies = movieService.searchBy(movieSpecification, moviePage);
    List<MovieDto> movieDtos = movies.stream().map(MovieDto::from)
        .collect(Collectors.toList());

    model.addAttribute("movieList", movieDtos);
    return "movie/movieList";
  }

  @GetMapping("display_add_movie")
  @PreAuthorize("hasAuthority('data:insert')")
  public String getAddMoviePage(Model model) {
    MovieDto movieDto = new MovieDto();
    movieDto.setSelectedPerformerDtoList(getPerformerList());
    model.addAttribute("genreList", getGenreList());
    model.addAttribute("languageList", getLanguageList());
    model.addAttribute("performerList", getPerformerList());
    model.addAttribute("movieDto", movieDto);
    return "movie/addMovie";
  }

  @PostMapping("add_movie")
  @PreAuthorize("hasAuthority('data:insert')")
  public String addMovie(@ModelAttribute MovieDto movieDto) {
    String fileName = StringUtils.cleanPath(movieDto.getMultipartFile().getOriginalFilename());
    movieDto.setMediaPath(fileName);
    List<Genre> genres = movieDto.getSelectedGenreIdList().stream()
        .map(genreId -> genreService.getGenreById(genreId))
        .collect(Collectors.toList());
    List<Language> languages = movieDto.getSelectedLanguageIdList().stream()
        .map(languageId -> languageService.getLanguageById(languageId))
        .collect(Collectors.toList());
    List<Performer> performers = movieDto.getSelectedPerformerDtoList().stream()
        .filter(p -> p.getPerformerId() != 0)
        .map(performerDto -> {
          Performer performer = performerService.getPerformerById(performerDto.getPerformerId());
          performer.setPerformerRole(performerDto.getPerformerRole());
          return performer;
        }).collect(Collectors.toList());

    Movie movie = movieService.addMovie(Movie.from(movieDto), genres, languages);

    saveMediaFile(movieDto, movie.getMovieId());

    moviePerformerService.addPerformersToMovie(movie, performers);

    return String.format("redirect:/movies/get_by_id/?id=%s", movie.getMovieId());
  }

  private void saveMediaFile(MovieDto movieDto, int movieId) {
    String fileName = StringUtils.cleanPath(movieDto.getMultipartFile().getOriginalFilename());
    movieDto.setMediaPath(fileName);

    try {
      String uploadDir = "./movie-photos/" + movieId;
      MediaUpload.saveFile(uploadDir, fileName, movieDto.getMultipartFile());
    } catch (IOException ioException) {
        new ImageUploadException(movieDto.getTitle());
    }
  }

  @RequestMapping(value = "delete_by_id", params = "id")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteMovieById(@RequestParam("id") int movieId) {
    movieService.deleteMovieById(movieId);
    return "redirect:/movies/get_all_movies";
  }

  @RequestMapping(value = "display_edit_by_id", params = "id")
  @PreAuthorize("hasAuthority('data:update')")
  public String editMovieById(@RequestParam("id") int movieId, Model model) {
    /*MovieDto movieDto = MovieDto.from(movieService.getMovieById(movieId));


    model.addAttribute("movieDto", movieDto);*/
    return "movie/editMovie";
  }

  @RequestMapping(value = "delete_assoc_movie_genre/", params = {"movieId", "genreId"})
  @PreAuthorize("hasAuthority('data:update')")
  public String removeAssocMovieGenre(@RequestParam("movieId") int movieId,
                                      @RequestParam("genreId") int genreId) {

    movieService.deleteMovieGenreAssoc(movieId, genreId);
    return String.format("redirect:/movies/get_by_id/?id=%s", movieId);
  }

  @RequestMapping(value = "delete_assoc_movie_language/", params = {"movieId", "languageId"})
  @PreAuthorize("hasAuthority('data:update')")
  public String removeAssocMovieLanguage(@RequestParam("movieId") int movieId,
                                      @RequestParam("languageId") int languageId) {

    movieService.deleteMovieLanguageAssoc(movieId, languageId);
    return String.format("redirect:/movies/get_by_id/?id=%s", movieId);
  }

  private List<GenreDto> getGenreList() {
    List<Genre> genres = genreService.getAllGenres();
    List<GenreDto> genreDtos = genres.stream().map(GenreDto::from)
        .collect(Collectors.toList());
    return genreDtos;
  }

  private List<LanguageDto> getLanguageList() {
    List<Language> languages = languageService.getAllLanguages();
    List<LanguageDto> languageDtos = languages.stream().map(LanguageDto::from)
        .collect(Collectors.toList());
    return languageDtos;
  }

  private List<PerformerDto> getPerformerList() {
    List<Performer> performers = performerService.getAllPerformers();
    List<PerformerDto> performerDtos = performers.stream().map(PerformerDto::from)
        .collect(Collectors.toList());
    return performerDtos;
  }

}
