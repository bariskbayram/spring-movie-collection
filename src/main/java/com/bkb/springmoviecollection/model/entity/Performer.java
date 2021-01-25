package com.bkb.springmoviecollection.model.entity;


import com.bkb.springmoviecollection.model.dto.PerformerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "performer")
public class Performer {

  @Id
  @SequenceGenerator(name = "performer_sequence", sequenceName = "performer_sequence",
      allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "performer_sequence"
  )
  @Column(name = "performer_id", updatable = false)
  private int performerId;

  @Column(name = "fullname", nullable = false)
  private String fullname;

  @OneToMany(mappedBy = "performer",
      cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MoviePerformer> moviesAssoc = new ArrayList<>();

  public Performer(String fullname) {
    this.fullname = fullname;
  }

  public static Performer from(PerformerDto performerDTO) {
    Performer performer = new Performer();
    performer.setFullname(performerDTO.getFullname());
    return performer;
  }

  @Override
  public String toString() {
    return "Genre{" +
        "id=" + performerId +
        ", fullname='" + fullname + '\'' +
        '}';
  }
}
