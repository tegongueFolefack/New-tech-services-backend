package com.example.securingweb.Models;


import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.BlogDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data

@AllArgsConstructor
@Entity
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titre;
	private String contenu;
	
	
    

    @Temporal(TemporalType.TIMESTAMP)
	private Date datePublication;
    
    @Temporal(TemporalType.TIMESTAMP)
	private Date dateModification;
    
    @PrePersist
    protected void onCreate() {
	 datePublication = new Date();
	 dateModification = new Date();
    }


	@ManyToOne
	private Admin admin;


	
	public BlogDTO toBlogDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, BlogDTO.class);
    }

	 
	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Blog(String titre, String contenu, Date datePublication, Date dateModification) {
		super();
		this.titre = titre;
		this.contenu = contenu;
		this.datePublication = datePublication;
		this.dateModification = dateModification;
	}
	
	
	
}
