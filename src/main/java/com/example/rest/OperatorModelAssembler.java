package com.example.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.persistence.domain.Operator;

@Component
public class OperatorModelAssembler implements RepresentationModelAssembler<Operator, EntityModel<Operator>> {

  @Override
  public EntityModel<Operator> toModel(Operator operator) {

    return new EntityModel<>(operator,
      linkTo(methodOn(OperatorController.class).one(operator.getId())).withSelfRel(),
      linkTo(methodOn(OperatorController.class).all()).withRel("operators"));
  }
}