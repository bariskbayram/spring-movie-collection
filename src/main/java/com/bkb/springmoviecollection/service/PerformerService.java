package com.bkb.springmoviecollection.service;

import com.bkb.springmoviecollection.model.entity.Performer;
import com.bkb.springmoviecollection.model.exception.TNotFoundException;
import com.bkb.springmoviecollection.repository.PerformerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformerService {

  private final PerformerRepository performerRepository;

  public PerformerService(PerformerRepository performerRepository) {
    this.performerRepository = performerRepository;
  }

  public List<Performer> getAllPerformers() {
    return performerRepository.findAll();
  }

  public Performer getPerformerById(int performerId) {
    return performerRepository.findById(performerId).orElseThrow( () ->
        new TNotFoundException(performerId, Performer.class));
  }

  public List<Performer> getPerformersByMovieId(int movieId) {
    return performerRepository.findAllByMovieId(movieId);
  }

  public Performer addPerformer(Performer performer) {
    return performerRepository.save(performer);
  }

  public void deletePerformerById(int id) {
    performerRepository.deleteById(id);
  }

  public void updatePerformerById(int performerId, String fullname) {
    performerRepository.updateById(performerId, fullname);
  }
}
