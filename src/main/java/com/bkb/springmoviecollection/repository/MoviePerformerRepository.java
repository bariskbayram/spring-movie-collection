package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.entity.MoviePerformer;
import com.bkb.springmoviecollection.model.entity.MoviePerformerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviePerformerRepository extends JpaRepository<MoviePerformer, MoviePerformerId> {
}
