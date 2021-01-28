package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Performer;
import lombok.Data;

@Data
public class PerformerDto {

  private int performerId;
  private String fullname = "";
  private String performerRole = "";

  public PerformerDto() {
  }

  public PerformerDto(int performerId, String performerRole) {
    this.performerId = performerId;
    this.performerRole = performerRole;
  }

  public PerformerDto(String fullname, int performerId) {
    this.performerId = performerId;
    this.fullname = fullname;
  }

  public static PerformerDto from(Performer performer) {
    PerformerDto performerDTO = new PerformerDto();
    performerDTO.setPerformerId(performer.getPerformerId());
    performerDTO.setFullname(performer.getFullname());
    return performerDTO;
  }
}
