package com.example.securingweb.Models;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.RealisationDTO;

import jakarta.persistence.Column;
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
public class Realisations {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private int id;
	
	private Categorie categorie;
	private String titre;

	private String description;

	 @Temporal(TemporalType.TIMESTAMP)
		private Date dateRealisation;
	    
	    @PrePersist
	    protected void onCreate() {
	    	dateRealisation = new Date();
	    }
	
	
	@ManyToOne
	private Admin admin;
	
	
	public RealisationDTO toRealisationDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, RealisationDTO.class);
    }


	public Realisations() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Realisations(String titre, Date dateRealisation, String description) {
		super();
		this.titre = titre;
		this.dateRealisation = dateRealisation;
		this.description = description;
	}

	
	
	

}
