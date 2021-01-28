package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

  List<Genre> findAllByMoviesMovieId(int movieId);

  @Modifying
  @Transactional
  @Query("update Genre g set g.genreName = :name where g.genreId = :genreId")
  void updateById(@Param("genreId") int genreId, @Param("name") String name);
}
