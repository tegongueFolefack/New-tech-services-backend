package com.example.securingweb.DTO;

import org.modelmapper.ModelMapper;


import com.example.securingweb.Models.Marque;

import lombok.Data;

@Data
public class MarqueDTO {
	private int id;
	private String libelle;
	
	public Marque toMarque() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Marque.class);
    }
}
