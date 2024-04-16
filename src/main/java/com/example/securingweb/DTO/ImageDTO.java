package com.example.securingweb.DTO;



import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Image;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class ImageDTO {

	private String libelle;
	
	@Lob
    @Column(length = 16777215)
	private byte[] imageData;
	
	public Image toImage() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Image.class);
    }
}
