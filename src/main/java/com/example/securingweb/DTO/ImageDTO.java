package com.example.securingweb.DTO;



import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Image;


import lombok.Data;

@Data
public class ImageDTO {

	private String libelle;
	
	
	public Image toImage() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Image.class);
    }
}
