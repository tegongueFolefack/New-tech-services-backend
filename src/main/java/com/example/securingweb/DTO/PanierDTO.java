package com.example.securingweb.DTO;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Panier;

import lombok.Data;

@Data
public class PanierDTO {
	
	private int total;
	
	public Panier toPanier() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Panier.class);
    }

}
