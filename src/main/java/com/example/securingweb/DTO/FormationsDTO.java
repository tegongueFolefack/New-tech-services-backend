package com.example.securingweb.DTO;

import java.time.LocalDate;
import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Formations;


import lombok.Data;

@Data
public class FormationsDTO {
	
	private String titre;
	private String description;
	private Date dateDebut;
	private int duree;
	private Double prix;
	    public Formations toFormations() {
	        ModelMapper modelMapper = new ModelMapper();
	        return modelMapper.map(this, Formations.class);
	    }
}
