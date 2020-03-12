package com.example.rest.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.models.Status;
import com.example.persistence.domain.Season;
import com.example.rest.controllers.SeasonController;

@Component
public class SeasonModelAssembler implements RepresentationModelAssembler<Season, EntityModel<Season>> {

  @Override
  public EntityModel<Season> toModel(Season season) {

    // Unconditional links to single-item resource and aggregate root

    EntityModel<Season> seasonModel = new EntityModel<>(season,
      linkTo(methodOn(SeasonController.class).one(season.getId())).withSelfRel(),
      linkTo(methodOn(SeasonController.class).all()).withRel("seasons")
    );

    // Conditional links based on state of the season

    if (season.getStatus() == Status.IN_PROGRESS) {
      seasonModel.add(
        linkTo(methodOn(SeasonController.class)
          .cancel(season.getId())).withRel("cancel"));
      seasonModel.add(
        linkTo(methodOn(SeasonController.class)
          .complete(season.getId())).withRel("complete"));
    }

    return seasonModel;
  }
}
