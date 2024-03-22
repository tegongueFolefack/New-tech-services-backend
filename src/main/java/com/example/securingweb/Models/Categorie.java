package com.example.securingweb.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.AdminDTO;
import com.example.securingweb.DTO.CategorieDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Categorie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String intitule;
	
	@JdbcTypeCode(SqlTypes.JSON)
	@OneToMany
	private List<Produit> produits = new ArrayList<>();
	
	public CategorieDTO toCategorieDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, CategorieDTO.class);
    }

	public Categorie() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Categorie(String intitule) {
		super();
		this.intitule = intitule;
	}
	
	


}
