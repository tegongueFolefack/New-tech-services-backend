package com.example.securingweb.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.BlogDTO;
import com.example.securingweb.DTO.CommandeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Commande {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private int id;
	
	private String statut;
	private Double montant;
	
	

    @Temporal(TemporalType.TIMESTAMP)
	private Date dateCmd;
	
	@ManyToMany
	private Collection<Client> clients = new ArrayList<>();
	
	@ManyToOne
	 private Client client;
	
	 

	
	@ManyToOne
	private Livraison livraison;
	
	@PrePersist
    protected void onCreate() {
		dateCmd = new Date();
    }
 
	
	public CommandeDTO toCommandeDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, CommandeDTO.class);
    }

	public Commande() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Commande(String statut, Double montant, Date dateCmd, Collection<Client> clients, Client client,
			Livraison livraison) {
		super();
		this.statut = statut;
		this.montant = montant;
		this.dateCmd = dateCmd;
		this.clients = clients;
		this.client = client;
		this.livraison = livraison;
	}

	
	
}
