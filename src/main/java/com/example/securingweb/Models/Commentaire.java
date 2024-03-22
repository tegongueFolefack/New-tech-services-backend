package com.example.securingweb.Models;

import java.util.Collection;
import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.CommentaireDTO;

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
public class Commentaire {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private int id;
	private String email;
	private String contenu;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEnvoie;
	
	@ManyToOne
	private Produit produit;
	
	
    
    @PrePersist
    protected void onCreate() {
    	dateEnvoie = new Date();
    }
	
	public CommentaireDTO toCommentaireDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, CommentaireDTO.class);
    }

	public Commentaire() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Commentaire(String email, String contenu, Date dateEnvoie, Produit produit) {
		super();
		this.email = email;
		this.contenu = contenu;
		this.dateEnvoie = dateEnvoie;
		this.produit = produit;
	}


}
