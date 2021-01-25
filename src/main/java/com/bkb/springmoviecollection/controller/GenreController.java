package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.GenreDto;
import com.bkb.springmoviecollection.model.entity.Genre;
import com.bkb.springmoviecollection.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
public class GenreController {

  private final GenreService genreService;

  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @GetMapping("get_all_genres")
  public List<GenreDto> getAllGenres() {
    List<Genre> genres = genreService.getAllGenres();
    List<GenreDto> genreDtos =
        genres.stream().map(GenreDto::from).collect(Collectors.toList());
    return genreDtos;
  }

  @GetMapping("get_by_id/{id}")
  public GenreDto getGenreById(@RequestParam("id") int genreId) {
    Genre genre = genreService.getGenreById(genreId);
    return GenreDto.from(genre);
  }

  @GetMapping("get_by_movie_id/{id}")
  public List<GenreDto> getGenreByMovieId(@RequestParam("id") int movieId) {
    List<Genre> genres = genreService.getGenreByMovieId(movieId);
    List<GenreDto> genreDtos = genres.stream()
        .map(GenreDto::from).collect(Collectors.toList());
    return genreDtos;
  }

  @PostMapping("add_genre")
  public GenreDto addGenre(@RequestBody GenreDto genreDTO) {
    Genre genre = genreService.addGenre(Genre.from(genreDTO));
    return GenreDto.from(genre);
  }

  @DeleteMapping("delete_genre_by_id/{id}")
  public void deleteGenreById(@RequestParam("id") int genreId) {
    genreService.deleteGenreById(genreId);
  }

}
