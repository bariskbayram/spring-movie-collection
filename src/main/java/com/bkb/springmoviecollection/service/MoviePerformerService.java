package com.bkb.springmoviecollection.service;

import com.bkb.springmoviecollection.model.entity.Movie;
import com.bkb.springmoviecollection.model.entity.MoviePerformer;
import com.bkb.springmoviecollection.model.entity.Performer;
import com.bkb.springmoviecollection.repository.MoviePerformerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviePerformerService {

  private final MoviePerformerRepository moviePerformerRepository;

  public MoviePerformerService(MoviePerformerRepository moviePerformerRepository) {
    this.moviePerformerRepository = moviePerformerRepository;
  }

  public void addPerformersToMovie(Movie movie, List<Performer> performerList) {
    performerList.stream().forEach(performer ->
        moviePerformerRepository.save(new MoviePerformer(movie, performer, performer.getPerformerRole())));
  }

  public void removeMoviePerformer(int movieId, int performerId) {
    moviePerformerRepository.removeMoviePerformer(movieId, performerId);
  }

  public void changeRole(int movieId, int performerId, String newRole) {
    moviePerformerRepository.changeRole(movieId, performerId, newRole);
  }

}
