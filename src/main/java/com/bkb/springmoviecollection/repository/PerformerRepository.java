package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.entity.Performer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformerRepository extends JpaRepository<Performer, Integer> {
}
