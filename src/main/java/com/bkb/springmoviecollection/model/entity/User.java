package com.bkb.springmoviecollection.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "username_unique", columnNames = "username"))
public class User {

  @Id
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence",
      allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "user_sequence"
  )
  @Column(name = "user_id", updatable = false)
  private int userId;

  @Column(name = "fullname", nullable = false)
  private String fullname;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "user_role", nullable = false)
  private String userRole;

}
