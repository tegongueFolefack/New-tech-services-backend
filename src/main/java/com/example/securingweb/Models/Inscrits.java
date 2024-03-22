package com.example.securingweb.Models;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.InscritsDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Inscrits {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private int id;
	
	private String email;
	private String nom;
	
	@ManyToOne
	private NewsLetters newsLetter;

	

	



	public InscritsDTO toInscritsDTO() {
		ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, InscritsDTO.class);
	}







	public Inscrits() {
		super();
		// TODO Auto-generated constructor stub
	}







	public Inscrits(String email, String nom, NewsLetters newsLetter) {
		super();
		this.email = email;
		this.nom = nom;
		this.newsLetter = newsLetter;
	}

}
