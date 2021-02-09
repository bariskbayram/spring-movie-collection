package com.bkb.springmoviecollection.model.entity;

import com.bkb.springmoviecollection.model.dto.GenreDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

  public static Genre from(GenreDto genreDto) {
    Genre genre = new Genre();
    genre.setGenreName(genreDto.getGenreName());
    return genre;
  }

  public static List<Genre> from(List<GenreDto> genreDtos) {
    return genreDtos.stream()
            .map(Genre::from)
            .collect(Collectors.toList());
  }

  @PreRemove
  public void removeMovies() {
    movies.forEach(movie -> movie.removeGenre(this));
  }

  @Override
  public String toString() {
    return "Genre{" +
        "id=" + genreId +
        ", name='" + genreName + '\'' +
        '}';
  }
}
