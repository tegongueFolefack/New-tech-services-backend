package com.example.securingweb.Models;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.ImageDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String libelle;
	
	@ManyToOne
	private Services service;
	
	

	public ImageDTO toImageDTO() {
		ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ImageDTO.class);
	}



	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Image(String libelle, Services service) {
		super();
		this.libelle = libelle;
		this.service = service;
	}


}
