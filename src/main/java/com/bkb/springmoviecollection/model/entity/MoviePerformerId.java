package com.bkb.springmoviecollection.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class MoviePerformerId implements Serializable {

  @Column(name = "movie_id")
  private int movieId;

  @Column(name = "performer_id")
  private int performerId;

  public MoviePerformerId(int movieId, int performerId) {
    this.movieId = movieId;
    this.performerId = performerId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(movieId, performerId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    MoviePerformerId moviePerformerId = (MoviePerformerId) obj;
    return Objects.equals(movieId, moviePerformerId.getMovieId()) &&
        Objects.equals(performerId, moviePerformerId.getPerformerId());
  }
}
