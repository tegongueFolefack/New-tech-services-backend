package com.example.securingweb.DTO;

import org.modelmapper.ModelMapper;


import com.example.securingweb.Models.NoteProduit;

import lombok.Data;

@Data
public class NoteProduitDTO {
	
	private Double note;
	
	public NoteProduit toNoteProduit() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, NoteProduit.class);
    }

}
