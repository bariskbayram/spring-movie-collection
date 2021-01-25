package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.PerformerDto;
import com.bkb.springmoviecollection.model.entity.Performer;
import com.bkb.springmoviecollection.service.PerformerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/performers")
public class PerformerController {

  private final PerformerService performerService;

  public PerformerController(PerformerService performerService) {
    this.performerService = performerService;
  }

  @GetMapping("get_all_performers")
  public List<PerformerDto> getAllPerformers() {
    List<Performer> performers = performerService.getAllPerformers();
    List<PerformerDto> performerDTOS =
        performers.stream().map(PerformerDto::from).collect(Collectors.toList());
    return performerDTOS;
  }

  @GetMapping("get_by_id/{id}")
  public PerformerDto getPerformerById(@RequestParam("id") int performerId) {
    Performer performer = performerService.getPerformerById(performerId);
    return PerformerDto.from(performer);
  }

  @PostMapping("add_performer")
  public PerformerDto addPerformer(@RequestBody PerformerDto performerDTO) {
    Performer performer = performerService.addPerformer(Performer.from(performerDTO));
    return PerformerDto.from(performer);
  }

  @DeleteMapping("delete_performer_by_id/{id}")
  public void deletePerformerById(@RequestParam("id") int performerId) {
    performerService.deletePerformerById(performerId);
  }

}
