package com.bkb.springmoviecollection.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movie_performer")
public class MoviePerformer {

  @EmbeddedId
  private MoviePerformerId moviePerformerId;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("movieId")
  @JoinColumn(name = "movie_id",
      foreignKey = @ForeignKey(name = "movie_performer_movie_id_fk"))
  private Movie movie;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("performerId")
  @JoinColumn(name = "performer_id",
      foreignKey = @ForeignKey(name = "movie_performer_performer_id_fk"))
  private Performer performer;

  @Column(name = "performer_role")
  private String performerRole;

  @Column(name = "created_at", nullable = false, updatable = false,
      insertable = false,
      columnDefinition = "TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  public MoviePerformer(Movie movie, Performer performer, String performerRole) {
    this.movie = movie;
    this.performer = performer;
    this.performerRole = performerRole;
    this.moviePerformerId = new MoviePerformerId(movie.getMovieId(), performer.getPerformerId());
  }
}
