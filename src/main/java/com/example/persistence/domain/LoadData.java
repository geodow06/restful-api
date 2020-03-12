package com.example.persistence.domain;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.models.Status;
import com.example.persistence.repository.OperatorRepository;
import com.example.persistence.repository.SeasonRepository;

@Configuration 
@Slf4j
public class LoadData {
	@Bean 
	CommandLineRunner initDatabase(OperatorRepository orderRepo, SeasonRepository seasonRepo) {
	    return args -> {
		      log.info("Preloading " + orderRepo.save(new Operator("Smoke", "SAS")));
		      log.info("Preloading " + orderRepo.save(new Operator("IQ", "GSG-9")));
		      
		      seasonRepo.save(new Season("Shifting Tides", Status.COMPLETED));
		      seasonRepo.save(new Season("Void Edge", Status.IN_PROGRESS));

		      seasonRepo.findAll().forEach(season -> {
		        log.info("Preloaded " + season);
		      });
	    };
	}
}
