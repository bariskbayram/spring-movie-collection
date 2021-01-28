package com.bkb.springmoviecollection.service;

import com.bkb.springmoviecollection.model.entity.Genre;
import com.bkb.springmoviecollection.model.exception.TNotFoundException;
import com.bkb.springmoviecollection.repository.GenreRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    return genreRepository.findById(genreId).orElseThrow(() ->
        new TNotFoundException(genreId, Genre.class));
  }

  public List<Genre> getGenreByMovieId(int movieId) {
    return genreRepository.findAllByMoviesMovieId(movieId);
  }

  public Genre addGenre(Genre genre) {
    return genreRepository.save(genre);
  }

  public void deleteGenreById(int genreId) {
    genreRepository.deleteById(genreId);
  }

  public void updateGenreById(int genreId, String genreName) {
    genreRepository.updateById(genreId, genreName);
  }
}
