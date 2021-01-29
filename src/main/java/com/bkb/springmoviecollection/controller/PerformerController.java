package com.bkb.springmoviecollection.controller;

import com.bkb.springmoviecollection.model.dto.PerformerDto;
import com.bkb.springmoviecollection.model.entity.Performer;
import com.bkb.springmoviecollection.service.PerformerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/performers")
public class PerformerController {

  private final PerformerService performerService;

  public PerformerController(PerformerService performerService) {
    this.performerService = performerService;
  }

  @GetMapping("get_all_performers")
  @PreAuthorize("hasAuthority('data:read')")
  public String getAllPerformers(Model model) {
    List<Performer> performers = performerService.getAllPerformers();
    List<PerformerDto> performerDTOS =
        performers.stream().map(PerformerDto::from).collect(Collectors.toList());
    model.addAttribute("performerList", performerDTOS);
    return "performer/performerList";
  }

  @GetMapping("get_by_id/{id}")
  @PreAuthorize("hasAuthority('data:read')")
  public PerformerDto getPerformerById(@RequestParam("id") int performerId) {
    Performer performer = performerService.getPerformerById(performerId);
    return PerformerDto.from(performer);
  }

  @PostMapping("add_performer")
  @PreAuthorize("hasAuthority('data:insert')")
  public String addPerformer(PerformerDto performerDTO) {
    performerService.addPerformer(Performer.from(performerDTO));
    return "redirect:/movies/display_add_movie";
  }

  @PostMapping("add_performer_from_list")
  @PreAuthorize("hasAuthority('data:insert')")
  public String addPerformerFromList(PerformerDto performerDTO) {
    performerService.addPerformer(Performer.from(performerDTO));
    return "redirect:/performers/get_all_performers";
  }

  @RequestMapping(value = "delete_performer_by_id/", params = "id")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deletePerformerById(@RequestParam("id") int performerId) {
    performerService.deletePerformerById(performerId);
    return "redirect:/performers/get_all_performers";
  }

  @RequestMapping(value = "update_performer_modal", params = {"id", "name"})
  @PreAuthorize("hasAuthority('data:update')")
  public String updateGenreModal(@RequestParam("id") int performerId,
                                 @RequestParam("name") String fullname,
                                 Model model) {

    model.addAttribute("title", "Edit Performer");
    model.addAttribute("url", String.format("/performers/update_performer_by_id/?id=%s", new Object[]{performerId}));
    model.addAttribute("modalId", "editPerformerModal");
    model.addAttribute("field", "fullname");
    model.addAttribute("performer", new PerformerDto(fullname, performerId));
    return "fragments :: editPerformerModal";
  }

  @RequestMapping(value = "update_performer_by_id/", params = {"id", "fullname"})
  @PreAuthorize("hasAuthority('data:update')")
  public String updateGenreById(@RequestParam("id") int performerId,
                                @RequestParam("fullname") String fullname) {
    performerService.updatePerformerById(performerId, fullname);
    return "redirect:/performers/get_all_performers";
  }

}
