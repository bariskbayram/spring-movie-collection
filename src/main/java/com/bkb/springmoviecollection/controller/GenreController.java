package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.GenreDTO;
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
  public List<GenreDTO> getAllGenres() {
    List<Genre> genres = genreService.getAllGenres();
    List<GenreDTO> genreDTOS =
        genres.stream().map(GenreDTO::from).collect(Collectors.toList());
    return genreDTOS;
  }

  @GetMapping("get_by_id/{id}")
  public GenreDTO getGenreById(@RequestParam("id") int genreId) {
    Genre genre = genreService.getGenreById(genreId);
    return GenreDTO.from(genre);
  }

  @PostMapping("add_genre")
  public GenreDTO addGenre(@RequestBody GenreDTO genreDTO) {
    Genre genre = genreService.addGenre(Genre.from(genreDTO));
    return GenreDTO.from(genre);
  }

  @DeleteMapping("delete_genre_by_id/{id}")
  public void deleteGenreById(@RequestParam("id") int id) {
    genreService.deleteGenreById(id);
  }

}
