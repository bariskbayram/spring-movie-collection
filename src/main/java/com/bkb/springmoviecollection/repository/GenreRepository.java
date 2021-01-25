package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {



}
