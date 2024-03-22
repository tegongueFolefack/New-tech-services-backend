package com.example.securingweb.Models;

import java.util.ArrayList;
import java.util.Collection;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.PanierDTO;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Panier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int total;
	
	@ManyToMany
	private Collection<Client> clients = new ArrayList<>();
	
	@ManyToMany
	private Collection<Produit> produits = new ArrayList<>();

	public PanierDTO toPanierDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, PanierDTO.class);
    }

	public Panier() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Panier(int total) {
		super();
		this.total = total;
	}
	
	

}
