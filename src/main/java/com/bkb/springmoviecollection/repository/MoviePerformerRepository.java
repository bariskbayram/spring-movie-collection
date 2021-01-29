package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.entity.MoviePerformer;
import com.bkb.springmoviecollection.model.entity.MoviePerformerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface MoviePerformerRepository extends JpaRepository<MoviePerformer, MoviePerformerId> {

  @Modifying
  @Transactional
  @Query("delete from MoviePerformer mp where mp.moviePerformerId.movieId = :movieId and mp.moviePerformerId.performerId = :performerId")
  void removeMoviePerformer(@Param("movieId") int movieId, @Param("performerId") int performerId);

  @Modifying
  @Transactional
  @Query("update MoviePerformer mp set mp.performerRole = :newRole where mp.moviePerformerId.movieId = :movieId and mp.moviePerformerId.performerId = :performerId")
  void changeRole(@Param("movieId") int movieId, @Param("performerId") int performerId, @Param("newRole") String newRole);

}
