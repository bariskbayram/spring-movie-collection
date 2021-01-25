package com.bkb.springmoviecollection.model.entity;

import com.bkb.springmoviecollection.model.dto.GenreDTO;
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
@Table(name = "genres")
public class Genre {

  @Id
  @SequenceGenerator(name = "genre_sequence", sequenceName = "genre_sequence",
      allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "genre_sequence"
  )
  @Column(name = "genre_id", updatable = false)
  private int genreId;

  @Column(name = "genre_name", nullable = false)
  private String genreName;

  @ManyToMany(mappedBy = "genres")
  private List<Movie> movies = new ArrayList<>();

  public Genre(String genreName) {
    this.genreName = genreName;
  }

  public Genre(int genreId) {
    this.genreId = genreId;
  }

  public Genre(int genreId, String genreName) {
    this.genreId = genreId;
    this.genreName = genreName;
  }

  public void addMovie(Movie movie) {
    movies.add(movie);
  }

  public static Genre from(GenreDTO genreDTO) {
    Genre genre = new Genre();
    genre.setGenreName(genreDTO.getName());
    return genre;
  }

  @Override
  public String toString() {
    return "Genre{" +
        "id=" + genreId +
        ", name='" + genreName + '\'' +
        '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(genreId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Genre genre = (Genre) obj;
    return Objects.equals(genreId, genre.getGenreId());
  }
}
