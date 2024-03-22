 package com.example.securingweb.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.*;
import com.example.securingweb.Repository.CommandeRepository;


@Service
public class CommandeService {
	
	@Autowired
    private CommandeRepository CommandeRepository;
 

	public Optional<Commande> getCommandeById(Integer id) {
		return CommandeRepository.findById(id);
	}

	public Iterable<Commande> getAllCommande() {
		return CommandeRepository.findAll();
	}

	public boolean deleteCommande(Integer id) {
		Optional<Commande> CommandeOpt = getCommandeById(id);
		if (CommandeOpt.isPresent()) {
			CommandeRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Commande updateCommande(Integer id, Commande Commande2) {
	    Optional<Commande> CommandeOpt = CommandeRepository.findById(id);
	    
	    if (CommandeOpt.isPresent()) {
	        Commande Commande = CommandeOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Commande avec les données du Commande2
	        Commande.setStatut(Commande2.getStatut());
	        Commande.setMontant(Commande2.getMontant());
	       
	        
	     // Mettre à jour la date de modification avec la date actuelle
	        Commande.setDateCmd(new Date()); // Utilisez LocalDateTime.now() pour obtenir la date actuelle
	        
	        // Sauvegarder les modifications dans la base de données
	        return CommandeRepository.save(Commande);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Commande saveCommande(Commande Commande) {
		return CommandeRepository.save(Commande);
	}
}
