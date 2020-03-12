package com.example.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.persistence.domain.Season;

public interface SeasonRepository extends JpaRepository<Season, Long> {

}
