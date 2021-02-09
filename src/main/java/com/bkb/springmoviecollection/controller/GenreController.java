package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.GenreDto;
import com.bkb.springmoviecollection.model.entity.Genre;
import com.bkb.springmoviecollection.service.GenreService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController {

  private final GenreService genreService;

  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @GetMapping("get_all_genres")
  @PreAuthorize("hasAuthority('data:read')")
  public String getAllGenres(Model model) {
    List<Genre> genres = genreService.getAllGenres();
    List<GenreDto> genreDtos = GenreDto.from(genres);
    model.addAttribute("genreList", genreDtos);
    return "genre/genreList";
  }

  @GetMapping("get_by_id/{id}")
  @PreAuthorize("hasAuthority('data:read')")
  public GenreDto getGenreById(@RequestParam("id") int genreId) {
    Genre genre = genreService.getGenreById(genreId);
    return GenreDto.from(genre);
  }

  @GetMapping("get_by_movie_id/{id}")
  @PreAuthorize("hasAuthority('data:read')")
  public List<GenreDto> getGenreByMovieId(@RequestParam("id") int movieId) {
    List<Genre> genres = genreService.getGenreByMovieId(movieId);
    return GenreDto.from(genres);
  }

  @PostMapping("add_genre")
  @PreAuthorize("hasAuthority('data:insert')")
  public String addGenre(GenreDto genreDTO) {
    genreService.addGenre(Genre.from(genreDTO));
    return "redirect:/movies/display_add_movie";
  }

  @PostMapping("add_genre_from_list")
  @PreAuthorize("hasAuthority('data:insert')")
  public String addGenreFromList(GenreDto genreDTO) {
    genreService.addGenre(Genre.from(genreDTO));
    return "redirect:/genres/get_all_genres";
  }

  @RequestMapping(value = "delete_genre_by_id/", params = "id")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteGenreById(@RequestParam("id") int genreId) {
    genreService.deleteGenreById(genreId);
    return "redirect:/genres/get_all_genres";
  }

  @RequestMapping(value = "update_genre_modal", params = {"id", "name"})
  @PreAuthorize("hasAuthority('data:update')")
  public String updateGenreModal(@RequestParam("id") int genreId,
                                 @RequestParam("name") String genreName,
                                 Model model) {

    //Trying to use same modal for all edit operations to be more modular
    model.addAttribute("title", "Edit Genre");
    model.addAttribute("url", String.format("/genres/update_genre_by_id/?id=%s", genreId));
    model.addAttribute("modalId", "editGenreModal");
    model.addAttribute("field", "genreName");
    model.addAttribute("value", genreName);
    return "fragments :: editModal";
  }

  @RequestMapping(value = "update_genre_by_id/", params = {"id", "genreName"})
  @PreAuthorize("hasAuthority('data:update')")
  public String updateGenreById(@RequestParam("id") int genreId,
                                @RequestParam("genreName") String genreName) {
    genreService.updateGenreById(genreId, genreName);
    return "redirect:/genres/get_all_genres";
  }

}
