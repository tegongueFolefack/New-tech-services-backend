package com.example.securingweb.DTO;

import org.modelmapper.ModelMapper;


import com.example.securingweb.Models.Services;

import lombok.Data;

@Data
public class ServicesDTO {

	
	private String nom;
	private String description;
	
	public Services toServices() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Services.class);
    }
}
