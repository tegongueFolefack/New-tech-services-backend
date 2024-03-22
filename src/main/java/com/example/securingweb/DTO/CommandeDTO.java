package com.example.securingweb.DTO;



import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Commande;


import lombok.Data;

@Data
public class CommandeDTO {
	
	private String statut;
	private Double montant;
	private Date dateCmd;
	
	
	
	public Commande toCommande() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Commande.class);
    }


}
