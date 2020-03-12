package com.example.persistence.domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.models.Status;

import lombok.Data;


@Entity
@Data
@Table(name = "SEASON")
public class Season {
	
	private @Id @GeneratedValue Long id;
	
	private String name;
	private Status status; 
	
	Season() {} 
	
	Season(String name, Status status) { 
		this.name = name; 
		this.status = status;
	}
}
