package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.dto.PerformerDto;
import com.bkb.springmoviecollection.model.entity.Performer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PerformerRepository extends JpaRepository<Performer, Integer> {

  @Query("select new com.bkb.springmoviecollection.model.dto.PerformerDto(p.performerId, p.fullname, ma.performerRole) from Performer p left outer join p.moviesAssoc ma left outer join ma.movie m where m.movieId = :id")
  List<PerformerDto> findAllByMovieId(@Param("id") int id);

  @Modifying
  @Transactional
  @Query("update Performer p set p.fullname = :fullname where p.performerId = :performerId")
  void updateById(@Param("performerId") int performerId, @Param("fullname") String fullname);

}
