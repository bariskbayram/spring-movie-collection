package com.bkb.springmoviecollection.model.search;

import com.bkb.springmoviecollection.model.entity.Movie;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Locale;

@Getter
@Setter
public class MovieSpecification implements Specification<Movie> {

  private String key;
  private String value;

  public MovieSpecification(String key, String value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public Predicate toPredicate(Root<Movie> root,
                               CriteriaQuery<?> criteriaQuery,
                               CriteriaBuilder criteriaBuilder) {

    if(key.equals("fullname")) {
      Join<Object, Object> p = root.join("performersAssoc").join("performer");
      criteriaQuery.distinct(true);
      return criteriaBuilder.like(
          criteriaBuilder.lower(p.get(key)), "%" + value.toLowerCase(Locale.ROOT) + "%");
    }else if(key.equals("genreName")) {
      Join<Object, Object> g = root.join("genres");
      criteriaQuery.distinct(true);
      return criteriaBuilder.like(
          criteriaBuilder.lower(g.get(key)), "%" + value.toLowerCase(Locale.ROOT) + "%");
    }
    return criteriaBuilder.like(
        criteriaBuilder.lower(root.get(key)), "%" + value.toLowerCase(Locale.ROOT) + "%");
  }
}
