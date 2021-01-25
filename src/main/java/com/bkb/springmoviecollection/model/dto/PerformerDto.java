package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Performer;
import lombok.Data;

@Data
public class PerformerDto {

  private int performerId;
  private String fullname;

  public PerformerDto() {
  }

  public static PerformerDto from(Performer performer) {
    PerformerDto performerDTO = new PerformerDto();
    performerDTO.setPerformerId(performer.getPerformerId());
    performerDTO.setFullname(performer.getFullname());
    return performerDTO;
  }
}
