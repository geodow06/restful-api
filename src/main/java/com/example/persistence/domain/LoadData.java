package com.example.persistence.domain;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.persistence.repository.OperatorRepository;

@Configuration 
@Slf4j
public class LoadData {
	@Bean 
	CommandLineRunner initDatabase(OperatorRepository repository) {
	    return args -> {
		      log.info("Preloading " + repository.save(new Operator("Smoke", "SAS")));
		      log.info("Preloading " + repository.save(new Operator("IQ", "GSG-9")));
	    };
	}
}
