package com.bkb.springmoviecollection.model.search;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class MoviePage {

  private int pageNumber = 0;
  private int pageSize = 10;
  private Sort.Direction direction =
      Sort.Direction.ASC;
  private String sortBy = "releaseYear";

}
