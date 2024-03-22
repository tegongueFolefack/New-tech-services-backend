package com.example.securingweb.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.LivraisonDTO;

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
public class Livraison {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String livrable;
	private Date dateLivraison;
	private String lieuLivraison;
	private String statut;
	
	@OneToMany
	private List<Commande> commande = new ArrayList<>();

	
	public LivraisonDTO toLivraisonDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, LivraisonDTO.class);
    }


	public Livraison() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Livraison(String livrable, Date dateLivraison, String lieuLivraison, String statut,
			List<Commande> commande) {
		super();
		this.livrable = livrable;
		this.dateLivraison = dateLivraison;
		this.lieuLivraison = lieuLivraison;
		this.statut = statut;
		this.commande = commande;
	}
	

}
