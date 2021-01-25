package com.bkb.springmoviecollection.model.entity;

import com.bkb.springmoviecollection.model.dto.GenreDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

  public static Genre from(GenreDto genreDTO) {
    Genre genre = new Genre();
    genre.setGenreName(genreDTO.getGenreName());
    return genre;
  }

  @Override
  public String toString() {
    return "Genre{" +
        "id=" + genreId +
        ", name='" + genreName + '\'' +
        '}';
  }
}
