package com.example.securingweb.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.ProduitDTO;
import com.example.securingweb.Enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "produit")
@DiscriminatorValue("produit")
public class Produit extends Services{
	
	public Produit(int id, String nom, String description, List<Image> images) {
		super(id, nom, description, images);
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private int id;
	
	private int quantiteDisponible;
	private Double prixUnitaire;
	
	@ManyToOne
	private Reduction reduction;
	
	@ManyToOne
	private Categorie categorie;
	
	@ManyToOne
	private Marque marque;
	
	@OneToMany
	private List<Like> like = new ArrayList<>();
	
	@OneToMany
	private List<Commentaire> commentaire = new ArrayList<>();
	
	
	
	@ManyToMany
	private Collection<Panier> panier = new ArrayList<>();
	
	
	public ProduitDTO toProduitDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProduitDTO.class);
    }


	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Produit(int quantiteDisponible, Double prixUnitaire, Reduction reduction) {
		super();
		this.quantiteDisponible = quantiteDisponible;
		this.prixUnitaire = prixUnitaire;
		this.reduction = reduction;
	}


	public Produit(String nom, String description) {
		super(nom, description);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
