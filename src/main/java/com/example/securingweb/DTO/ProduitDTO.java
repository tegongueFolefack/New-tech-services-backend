package com.example.securingweb.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Categorie;
import com.example.securingweb.Models.Commentaire;
import com.example.securingweb.Models.Image;
import com.example.securingweb.Models.Like;
import com.example.securingweb.Models.Marque;
import com.example.securingweb.Models.Panier;
import com.example.securingweb.Models.Produit;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class ProduitDTO extends ServicesDTO{
	
	
	
	private int id;
	private int quantiteDisponible;
	private Double prixUnitaire;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Categorie categorie;
	@ManyToOne(fetch = FetchType.EAGER)
	private Image image;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Marque marque;
	
	@OneToMany
	private List<Like> like = new ArrayList<>();
	
	@OneToMany
	private List<Commentaire> commentaire = new ArrayList<>();
	
	
	
	@ManyToMany
	private Collection<Panier> panier = new ArrayList<>();
	

	public Produit toProduit() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Produit.class);
    }
}
