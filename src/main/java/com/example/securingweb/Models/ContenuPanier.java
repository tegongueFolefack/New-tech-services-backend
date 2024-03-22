package com.example.securingweb.Models;

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
public class ContenuPanier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
	
	@ManyToOne
	 private Produit produit;
	
	@ManyToOne
	private Panier panier;

	public ContenuPanier() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	 
}
