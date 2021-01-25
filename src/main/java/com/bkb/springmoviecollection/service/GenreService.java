package com.bkb.springmoviecollection.service;

import com.bkb.springmoviecollection.model.entity.Genre;
import com.bkb.springmoviecollection.model.exception.TNotFoundException;
import com.bkb.springmoviecollection.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

  private final GenreRepository genreRepository;

  public GenreService(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  public List<Genre> getAllGenres() {
    return genreRepository.findAll();
  }

  public Genre getGenreById(int genreId) {
    return genreRepository.findById(genreId).orElseThrow( () ->
        new TNotFoundException(genreId, Genre.class));
  }

  public Genre addGenre(Genre genre) {
    return genreRepository.save(genre);
  }

  public void deleteGenreById(int id) {
    genreRepository.deleteById(id);
  }



}
