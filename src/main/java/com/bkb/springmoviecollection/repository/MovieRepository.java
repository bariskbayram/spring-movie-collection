package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {



}
