package com.example.rest;

public class SeasonNotFoundException extends RuntimeException {

	  public SeasonNotFoundException(Long id) {
	    super("Could not find season " + id);
	  }
}
