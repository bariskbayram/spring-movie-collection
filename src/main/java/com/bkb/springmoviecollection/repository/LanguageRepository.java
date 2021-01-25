package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

  List<Language> findAllByMoviesMovieId(int movieId);

}
