package com.example.rest;


import java.util.List;

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

  OperatorController(OperatorRepository repository) {
    this.repository = repository;
  }

  // Aggregate root

  @GetMapping("/operators")
  List<Operator> all() {
    return repository.findAll();
  }

  @PostMapping("/operators")
  Operator newOperator(@RequestBody Operator newOperator) {
    return repository.save(newOperator);
  }

  // Single item

  @GetMapping("/employees/{id}")
  EntityModel<Operator> one(@PathVariable Long id) {

    Operator operator = repository.findById(id)
      .orElseThrow(() -> new OperatorNotFoundException(id));

    return new EntityModel<>(operator,
      linkTo(methodOn(OperatorController.class).one(id)).withSelfRel(),
      linkTo(methodOn(OperatorController.class).all()).withRel("operators"));
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
