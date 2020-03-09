package com.example.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.persistence.domain.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Long>{

}
