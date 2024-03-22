package com.example.securingweb.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.ReductionDTO;

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
public class Reduction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Double montant;
	private Date dateDebut;
	private int duree;
	
	@OneToMany
	private List<Produit> produit = new ArrayList<>();

	public ReductionDTO toReductionDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ReductionDTO.class);
    }

	public Reduction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reduction(Double montant, Date dateDebut, int duree) {
		super();
		this.montant = montant;
		this.dateDebut = dateDebut;
		this.duree = duree;
	}
	
	
}
