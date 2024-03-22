package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Categorie;
import com.example.securingweb.Models.Realisations;

import lombok.Data;

@Data
public class RealisationDTO {

	private Categorie categorie;
	private String titre;
	private Date dateRealisation;
	private String description;
	
	public Realisations toRealisation() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Realisations.class);
    }
}
