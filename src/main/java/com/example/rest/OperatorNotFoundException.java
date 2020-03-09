package com.example.rest;

public class OperatorNotFoundException extends RuntimeException {

  OperatorNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}