package com.bkb.springmoviecollection.service;

import com.bkb.springmoviecollection.model.entity.Genre;
import com.bkb.springmoviecollection.model.entity.Movie;
import com.bkb.springmoviecollection.model.exception.TNotFoundException;
import com.bkb.springmoviecollection.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> getAllMovies() {
    return movieRepository.findAll();
  }

  public Movie getMovieById(int movieId) {
    return movieRepository.findById(movieId).orElseThrow( () ->
        new TNotFoundException(movieId, Movie.class));
  }

  public Movie addMovie(Movie movie, List<Genre> genres) {
    genres.stream().forEach(genre -> movie.addGenre(genre));
    return movieRepository.save(movie);
  }

}
