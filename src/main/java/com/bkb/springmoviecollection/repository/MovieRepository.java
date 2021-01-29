package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface MovieRepository extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {

  @Transactional
  @Modifying
  @Query(value = "delete from movie_genre where movie_id = :movieId and genre_id = :genreId", nativeQuery = true)
  void deleteMovieGenreAssoc(@Param("movieId") int movieId, @Param("genreId") int genreId);

  @Transactional
  @Modifying
  @Query(value = "delete from movie_language where movie_id = :movieId and language_id = :languageId", nativeQuery = true)
  void deleteMovieLanguageAssoc(@Param("movieId") int movieId, @Param("languageId") int languageId);

}
