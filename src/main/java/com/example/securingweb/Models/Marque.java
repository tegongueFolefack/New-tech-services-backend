package com.example.securingweb.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.MarqueDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Marque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String libelle;
	
	@OneToMany
	private List<Produit> produit = new ArrayList<>();

	public MarqueDTO toMarqueDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, MarqueDTO.class);
    }

	public Marque() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Marque(String libelle) {
		super();
		this.libelle = libelle;
	}
	
	

}
