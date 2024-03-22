package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Inscrits;


import lombok.Data;

@Data
public class InscritsDTO {
	

	
	private String email;
	private String nom;
	
	public Inscrits toInscrits() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Inscrits.class);
    }

}
