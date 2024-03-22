package com.example.securingweb.Models;



import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.AdminDTO;
import com.example.securingweb.DTO.FormationsDTO;
import com.example.securingweb.DTO.ImageDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Formations {
	
	public Formations() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String titre;
	private String description;
	
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDebut;
	
	private int duree;
	
	private Double prix;
	
	@PrePersist
    protected void onCreate() {
		dateDebut = new Date();
	
    }
	
	@ManyToOne
	private Admin admin;
	
	

	public FormationsDTO toFormationsDTO() {
		ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, FormationsDTO.class);
	}



	public Formations(String titre, String description, Date dateDebut, int duree, Double prix, Admin admin) {
		super();
		this.titre = titre;
		this.description = description;
		this.dateDebut = dateDebut;
		this.duree = duree;
		this.prix = prix;
		this.admin = admin;
	}

	
	
	
	

	
	
	

}
