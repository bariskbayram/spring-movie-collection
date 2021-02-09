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

    //Casting Movie to MovieDto, it's not necessary for now
    Page<Movie> movies = movieService.getAllMovies(moviePage);
    List<MovieDto> movieDtos = MovieDto.from(movies.toList());

    model.addAttribute("movieList", movieDtos);
    return "movie/movieList";
  }

  @GetMapping(value = "get_by_id/", params = "id")
  @PreAuthorize("hasAuthority('data:read')")
  public String getMovieById(@RequestParam("id") int movieId, Model model) {
    Movie movie = movieService.getMovieById(movieId);

    //Casting Genre, Language to GenreDto, LanguageDto, it's not necessary for now
    List<Genre> genres = genreService.getGenreByMovieId(movieId);
    List<GenreDto> genreDtos = GenreDto.from(genres);
    List<Language> languages = languageService.getLanguageByMovieId(movieId);
    List<LanguageDto> languageDtos = LanguageDto.from(languages);
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

    //Casting Movie to DTO, it's not necessary for now
    Page<Movie> movies = movieService.searchBy(movieSpecification, moviePage);
    List<MovieDto> movieDtos = MovieDto.from(movies.toList());

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
        .map(genreService::getGenreById)
        .collect(Collectors.toList());
    List<Language> languages = movieDto.getSelectedLanguageIdList().stream()
        .map(languageService::getLanguageById)
        .collect(Collectors.toList());
    List<Performer> performers = movieDto.getSelectedPerformerDtoList().stream()
        .filter(p -> p.getPerformerId() != 0)
        .map(performerDto -> {
          Performer performer = performerService.getPerformerById(performerDto.getPerformerId());
          performer.setPerformerRole(performerDto.getPerformerRole());
          return performer;
        }).collect(Collectors.toList());

    Movie movie = movieService.addMovie(Movie.from(movieDto), genres, languages);

    try {
      saveMediaFile(movieDto, movie.getMovieId());
    } catch (ImageUploadException e) {
      e.printStackTrace();
    }

    moviePerformerService.addPerformersToMovie(movie, performers);

    return String.format("redirect:/movies/get_by_id/?id=%s", movie.getMovieId());
  }

  private void saveMediaFile(MovieDto movieDto, int movieId) throws ImageUploadException {
    String fileName = StringUtils.cleanPath(movieDto.getMultipartFile().getOriginalFilename());
    movieDto.setMediaPath(fileName);

    try {
      String uploadDir = "./movie-photos/" + movieId;
      MediaUpload.saveFile(uploadDir, fileName, movieDto.getMultipartFile());
    } catch (IOException ioException) {
        throw new ImageUploadException(movieDto.getTitle());
    }
  }

  @RequestMapping(value = "delete_by_id", params = "id")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteMovieById(@RequestParam("id") int movieId) {
    movieService.deleteMovieById(movieId);
    return "redirect:/movies/get_all_movies";
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

  @RequestMapping(value = "removeassoc_movie_performer", params = {"movieId", "performerId"})
  @PreAuthorize("hasAuthority('data:update')")
  public String removeAssocMoviePerformer(@RequestParam("movieId") int movieId,
                                          @RequestParam("performerId") int performerId) {

    moviePerformerService.removeMoviePerformer(movieId, performerId);
    return String.format("redirect:/movies/get_by_id/?id=%s", movieId);
  }

  @RequestMapping(value = "update_role_modal/", params = {"movieId", "performerId", "currentRole"})
  @PreAuthorize("hasAuthority('data:update')")
  public String updateRoleModal(@RequestParam("movieId") int movieId,
                                 @RequestParam("performerId") int performerId,
                                 @RequestParam("currentRole") String currentRole,
                                 Model model) {

    //Trying to use same modal for all edit operations to be more modular
    model.addAttribute("title", "Edit Performer Role");
    model.addAttribute("url",
        String.format("/movies/update_role/?movieId=%s&performerId=%s", new Object[]{movieId, performerId}));
    model.addAttribute("modalId", "editPerformerRoleModal");
    model.addAttribute("field", "newRole");
    model.addAttribute("value", currentRole);
    return "fragments :: editModal";
  }

  @RequestMapping(value = "update_role/", params = {"movieId", "performerId", "newRole"})
  @PreAuthorize("hasAuthority('data:update')")
  public String updateGenreById(@RequestParam("movieId") int movieId,
                                @RequestParam("performerId") int performerId,
                                @RequestParam("newRole") String newRole) {
    moviePerformerService.changeRole(movieId, performerId, newRole);
    return String.format("redirect:/movies/get_by_id/?id=%s", new Object[]{movieId});
  }

  @RequestMapping(value = "add_performer_to_movie_modal/", params = "movieId")
  @PreAuthorize("hasAuthority('data:update')")
  public String addPerformerToMovieModal(@RequestParam("movieId") int movieId, Model model) {
    List<Performer> performers = performerService.getAllPerformers();
    List<PerformerDto> performerDtos = PerformerDto.from(performers);

    model.addAttribute("performerList", performerDtos);
    model.addAttribute("url",
        String.format("/movies/add_performer_to_movie/?movieId=%s", new Object[]{movieId}));
    model.addAttribute("movieDto", new MovieDto());
    return "fragments :: addPerformerModal";
  }

  @RequestMapping(value = "add_performer_to_movie/", params = "movieId")
  @PreAuthorize("hasAuthority('data:update')")
  public String addPerformerToMovie(@RequestParam("movieId") int movieId,
                                    MovieDto movieDto) {

    List<Performer> performers = movieDto.getSelectedPerformerDtoList().stream()
        .filter(p -> p.getPerformerId() != 0)
        .map(performerDto -> {
          Performer performer = performerService.getPerformerById(performerDto.getPerformerId());
          performer.setPerformerRole(performerDto.getPerformerRole());
          return performer;
        }).collect(Collectors.toList());

    Movie movie = movieService.getMovieById(movieId);
    moviePerformerService.addPerformersToMovie(movie, performers);
    return String.format("redirect:/movies/get_by_id/?id=%s", movieId);
  }

  private List<GenreDto> getGenreList() {
    List<Genre> genres = genreService.getAllGenres();
    return GenreDto.from(genres);
  }

  private List<LanguageDto> getLanguageList() {
    List<Language> languages = languageService.getAllLanguages();
    return LanguageDto.from(languages);
  }

  private List<PerformerDto> getPerformerList() {
    List<Performer> performers = performerService.getAllPerformers();
    return PerformerDto.from(performers);
  }

}
