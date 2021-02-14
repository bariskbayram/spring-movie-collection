package com.bkb.springmoviecollection.model.dto;

import com.bkb.springmoviecollection.model.entity.Performer;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PerformerDto {

  private int performerId;
  private String fullname = "";
  private String performerRole = "";

  public PerformerDto() {
  }

  public PerformerDto(int performerId, String fullname, String performerRole) {
    this.performerId = performerId;
    this.fullname = fullname;
    this.performerRole = performerRole;
  }

  public static PerformerDto from(Performer performer) {
    PerformerDto performerDTO = new PerformerDto();
    performerDTO.setPerformerId(performer.getPerformerId());
    performerDTO.setFullname(performer.getFullname());
    return performerDTO;
  }

  public static List<PerformerDto> from(List<Performer> performers) {
    return performers.stream()
            .map(PerformerDto::from)
            .collect(Collectors.toList());
  }
}
