package com.bkb.springmoviecollection.service;

import com.bkb.springmoviecollection.model.entity.*;
import com.bkb.springmoviecollection.model.exception.TNotFoundException;
import com.bkb.springmoviecollection.model.search.MoviePage;
import com.bkb.springmoviecollection.model.search.MovieSpecification;
import com.bkb.springmoviecollection.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public Page<Movie> getAllMovies(MoviePage moviePage) {
    Sort sort = Sort.by(moviePage.getDirection(), moviePage.getSortBy());

    Pageable pageable = PageRequest.of(moviePage.getPageNumber(),
        moviePage.getPageSize(), sort);

    return movieRepository.findAll(pageable);
  }

  public Movie getMovieById(int movieId) {
    return movieRepository.findById(movieId).orElseThrow( () ->
        new TNotFoundException(movieId, Movie.class));
  }

  public Movie addMovie(Movie movie,
                        List<Genre> genres,
                        List<Language> languages) {

    languages.stream().forEach(language -> movie.addLanguage(language));
    genres.stream().forEach(genre -> movie.addGenre(genre));
    return movieRepository.save(movie);
  }

  public Page<Movie> searchBy(MovieSpecification movieSpecification,
                              MoviePage moviePage) {

    Sort sort = Sort.by(moviePage.getDirection(), moviePage.getSortBy());
    Pageable pageable = PageRequest.of(moviePage.getPageNumber(),
        moviePage.getPageSize(), sort);
    return movieRepository.findAll(movieSpecification, pageable);
  }

  public void deleteMovieById(int movieId) {
    movieRepository.deleteById(movieId);
  }
}
