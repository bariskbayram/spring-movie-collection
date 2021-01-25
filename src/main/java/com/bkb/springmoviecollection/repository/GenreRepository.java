package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

  List<Genre> findAllByMoviesMovieId(int movieId);

}
