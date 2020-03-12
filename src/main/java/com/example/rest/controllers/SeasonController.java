package com.example.rest.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Status;
import com.example.persistence.domain.Season;
import com.example.persistence.repository.SeasonRepository;
import com.example.rest.SeasonNotFoundException;
import com.example.rest.assemblers.SeasonModelAssembler;

@RestController
public class SeasonController {

  private final SeasonRepository seasonRepository;
  private final SeasonModelAssembler assembler;

  SeasonController(SeasonRepository seasonRepository,
                    SeasonModelAssembler assembler) {

    this.seasonRepository = seasonRepository;
    this.assembler = assembler;
  }

  @GetMapping("/seasons")
  public CollectionModel<EntityModel<Season>> all() {

    List<EntityModel<Season>> seasons = seasonRepository.findAll().stream()
      .map(assembler::toModel)
      .collect(Collectors.toList());

    return new CollectionModel<>(seasons,
      linkTo(methodOn(SeasonController.class).all()).withSelfRel());
  }

  @GetMapping("/seasons/{id}")
  public EntityModel<Season> one(@PathVariable Long id) {
    Season season = seasonRepository.findById(id)
        .orElseThrow(() -> new SeasonNotFoundException(id));

    return assembler.toModel(season);
  }

  @PostMapping("/seasons")
  public ResponseEntity<EntityModel<Season>> newSeason(@RequestBody Season season) {

    season.setStatus(Status.IN_PROGRESS);
    Season newSeason = seasonRepository.save(season);

    return ResponseEntity
      .created(linkTo(methodOn(SeasonController.class).one(newSeason.getId())).toUri())
      .body(assembler.toModel(newSeason));
  }
  
  @DeleteMapping("/seasons/{id}/cancel")
  public ResponseEntity<RepresentationModel> cancel(@PathVariable Long id) {

    Season season = seasonRepository.findById(id).orElseThrow(() -> new SeasonNotFoundException(id));

    if (season.getStatus() == Status.IN_PROGRESS) {
      season.setStatus(Status.CANCELLED);
      return ResponseEntity.ok(assembler.toModel(seasonRepository.save(season)));
    }

    return ResponseEntity
      .status(HttpStatus.METHOD_NOT_ALLOWED)
      .body(new VndErrors.VndError("Method not allowed", "You can't cancel a season that is in the " + season.getStatus() + " status"));
  }
  
  @PutMapping("/seasons/{id}/complete")
  public ResponseEntity<RepresentationModel> complete(@PathVariable Long id) {

      Season season = seasonRepository.findById(id).orElseThrow(() -> new SeasonNotFoundException(id));

      if (season.getStatus() == Status.IN_PROGRESS) {
        season.setStatus(Status.COMPLETED);
        return ResponseEntity.ok(assembler.toModel(seasonRepository.save(season)));
      }

      return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(new VndErrors.VndError("Method not allowed", "You can't complete a season that is in the " + season.getStatus() + " status"));
  }
}