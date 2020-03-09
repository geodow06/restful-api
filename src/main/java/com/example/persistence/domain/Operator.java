package com.example.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Operator {
	private @Id @GeneratedValue Long id; 
	private String name; 
	private String ctu; 
	
	Operator() {} 
	
	Operator(String name, String ctu) { 
		this.name = name; 
		this.ctu = ctu;
	}
}
