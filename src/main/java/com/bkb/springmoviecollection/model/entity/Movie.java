package com.bkb.springmoviecollection.model.entity;

import com.bkb.springmoviecollection.model.dto.MovieDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @SequenceGenerator(name = "movie_sequence", sequenceName = "movie_sequence",
      allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "movie_sequence"
  )
  @Column(name = "movie_id", updatable = false)
  private int movieId;

  @Column(name = "movie_title", nullable = false)
  private String title;

  @Column(name = "release_year", nullable = false)
  private int releaseYear;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "media_path", nullable = false)
  private String mediaPath;

  @ManyToMany
  @JoinTable(name = "movie_genre",
      joinColumns = @JoinColumn(name = "movie_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id"),
      uniqueConstraints = @UniqueConstraint(name = "movie_genre_pk", columnNames = {"movie_id", "genre_id"}))
  private List<Genre> genres = new ArrayList<>();

  public Movie(String title, int releaseYear, String description, String mediaPath) {
    this.title = title;
    this.releaseYear = releaseYear;
    this.description = description;
    this.mediaPath = mediaPath;
  }

  public void addGenre(Genre genre) {
    genres.add(genre);
  }

  public static Movie from(MovieDTO movieDTO) {
    Movie movie = new Movie();
    movie.setTitle(movieDTO.getTitle());
    movie.setDescription(movieDTO.getDescription());
    movie.setReleaseYear(movieDTO.getReleaseYear());
    movie.setMediaPath(movieDTO.getMediaPath());
    return movie;
  }

  @Override
  public String toString() {
    return "Movie{" +
        "movie_id=" + movieId +
        ", title='" + title + '\'' +
        '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(movieId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Movie movie = (Movie) obj;
    return Objects.equals(movieId, movie.getMovieId());
  }
}
