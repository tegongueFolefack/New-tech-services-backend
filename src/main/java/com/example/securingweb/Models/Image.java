package com.example.securingweb.Models;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.ImageDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	
	@Lob
    @Column(length = 16777215)
	private byte[] imageData;
	
//	@ManyToOne
//	private Services service;
	
	@OneToMany
	private List<Produit> produits = new ArrayList<>();
	
	

	public ImageDTO toImageDTO() {
		ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ImageDTO.class);
	}



	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Image(String libelle) {
		super();
		this.libelle = libelle;
	}


}