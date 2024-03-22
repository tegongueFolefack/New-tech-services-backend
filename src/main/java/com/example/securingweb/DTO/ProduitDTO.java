package com.example.securingweb.DTO;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.NoteProduit;
import com.example.securingweb.Models.Produit;

import lombok.Data;

@Data
public class ProduitDTO {
	
	private int quantiteDisponible;
	private Double prixUnitaire;


	public Produit toProduit() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Produit.class);
    }
}
