package com.bkb.springmoviecollection.model.search;

import com.bkb.springmoviecollection.model.entity.Movie;
import com.bkb.springmoviecollection.model.entity.Performer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@Getter
@Setter
public class MovieSpecification implements Specification<Movie> {

  private String key;
  private String value;
  private boolean isPerformer = false;

  public MovieSpecification(String key, String value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public Predicate toPredicate(Root<Movie> root,
                               CriteriaQuery<?> criteriaQuery,
                               CriteriaBuilder criteriaBuilder) {

    if(isPerformer) {
      Join<Object, Object> h = root.join("performer");
      return criteriaBuilder.like(h.get(key), "%bkb%");
    }
    return criteriaBuilder.like(
        root.get(key), "%" + value + "%");
  }
}
