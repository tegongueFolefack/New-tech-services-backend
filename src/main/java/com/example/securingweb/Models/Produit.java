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
import jakarta.persistence.FetchType;
import jakarta.persistence.FetchType;
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
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "produit")
@DiscriminatorValue("produit")
public class Produit extends Services{
	
	
	
	
	private int quantiteDisponible;
	private Double prixUnitaire;
	
//	@ManyToOne
//	private Reduction reduction;
//	
	@ManyToOne(fetch = FetchType.EAGER )
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
	
	
	public ProduitDTO toProduitDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProduitDTO.class);
    }


	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Produit(int quantiteDisponible, Double prixUnitaire, Reduction reduction, Categorie categorie, Marque marque,
			List<Like> like, List<Commentaire> commentaire, Collection<Panier> panier) {
		super();
		this.quantiteDisponible = quantiteDisponible;
		this.prixUnitaire = prixUnitaire;
		this.categorie = categorie;
		this.marque = marque;
		this.like = like;
		this.commentaire = commentaire;
		this.panier = panier;
	}


	public Produit(int id, String nom, String description, List<Image> images) {
		super(id, nom, description);
		// TODO Auto-generated constructor stub
	}


	public Produit(String nom, String description) {
		super(nom, description);
		// TODO Auto-generated constructor stub
	}


	


	public Produit(int id, int quantiteDisponible, Double prixUnitaire, Reduction reduction, Categorie categorie,
			Marque marque, List<Like> like, List<Commentaire> commentaire, Collection<Panier> panier) {
		super();
		this.quantiteDisponible = quantiteDisponible;
		this.prixUnitaire = prixUnitaire;
		this.categorie = categorie;
		this.marque = marque;
		this.like = like;
		this.commentaire = commentaire;
		this.panier = panier;
	}


	public Produit(String nom, String description,int quantiteDisponible, Double prixUnitaire) {
		super(nom, description);
		this.quantiteDisponible = quantiteDisponible;
		this.prixUnitaire = prixUnitaire;
	}


	public Produit(int id, int quantiteDisponible, Double prixUnitaire, Categorie categorie, Image image, Marque marque,
			List<Like> like, List<Commentaire> commentaire, Collection<Panier> panier) {
		super();
		this.quantiteDisponible = quantiteDisponible;
		this.prixUnitaire = prixUnitaire;
		this.categorie = categorie;
		this.image = image;
		this.marque = marque;
		this.like = like;
		this.commentaire = commentaire;
		this.panier = panier;
	}


	

	
	

}
