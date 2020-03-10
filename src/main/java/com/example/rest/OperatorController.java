package com.example.rest;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.persistence.domain.Operator;
import com.example.persistence.repository.OperatorRepository;

@RestController
public class OperatorController {

  private final OperatorRepository repository;
  
  private final OperatorModelAssembler assembler;

  OperatorController(OperatorRepository repository, OperatorModelAssembler assembler) {
    this.repository = repository; 
    this.assembler = assembler;
  }

  // Aggregate root

  @GetMapping("/operators")
  CollectionModel<EntityModel<Operator>> all() {

    List<EntityModel<Operator>> operators = repository.findAll().stream()
      .map(assembler::toModel)
      .collect(Collectors.toList());

    return new CollectionModel<>(operators,
      linkTo(methodOn(OperatorController.class).all()).withSelfRel());
  }

  @PostMapping("/operators")
  Operator newOperator(@RequestBody Operator newOperator) {
    return repository.save(newOperator);
  }

  // Single item

  @GetMapping("/operators/{id}")
  EntityModel<Operator> one(@PathVariable Long id) {

    Operator operator = repository.findById(id)
      .orElseThrow(() -> new OperatorNotFoundException(id));

    return assembler.toModel(operator);
  }

  @PutMapping("/operators/{id}")
  Operator replaceOperator(@RequestBody Operator newOperator, @PathVariable Long id) {

    return repository.findById(id)
      .map(operator -> {
        operator.setName(newOperator.getName());
        operator.setCtu(newOperator.getCtu());
        return repository.save(operator);
      })
      .orElseGet(() -> {
        newOperator.setId(id);
        return repository.save(newOperator);
      });
  }

  @DeleteMapping("/operators/{id}")
  void deleteOperator(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
