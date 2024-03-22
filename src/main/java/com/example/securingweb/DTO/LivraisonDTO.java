package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;


import com.example.securingweb.Models.Livraison;

import lombok.Data;

@Data
public class LivraisonDTO {

	private String livrable;
	private Date dateLivraison;
	private String lieuLivraison;
	private String statut;
	
	public Livraison toLivraison() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Livraison.class);
    }
}
