package com.bkb.springmoviecollection.repository;

import com.bkb.springmoviecollection.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByUsername(String username);

  @Modifying
  @Transactional
  @Query("update User s set s.userRole = :userRole where s.userId = :id")
  void updateUserRole(@Param("id") int id, @Param("userRole") String userRole);
}
