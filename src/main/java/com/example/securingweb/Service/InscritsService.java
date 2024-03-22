package com.example.securingweb.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.*;
import com.example.securingweb.Repository.*;



@Service
public class InscritsService {
	
	
	@Autowired
    private InscritsRepository InscritsRepository;
 

	public Optional<Inscrits> getInscritsById(Integer id) {
		return InscritsRepository.findById(id);
	}

	public Iterable<Inscrits> getAllInscrits() {
		return InscritsRepository.findAll();
	}

	public boolean deleteInscrits(Integer id) {
		Optional<Inscrits> InscritsOpt = getInscritsById(id);
		if (InscritsOpt.isPresent()) {
			InscritsRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Inscrits updateInscrits(Integer id, Inscrits Inscrits2) {
	    Optional<Inscrits> InscritsOpt = InscritsRepository.findById(id);
	    
	    if (InscritsOpt.isPresent()) {
	    	Inscrits Inscrits = InscritsOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Inscrits.setEmail(Inscrits2.getEmail());
	    	Inscrits.setNom(Inscrits2.getNom());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return InscritsRepository.save(Inscrits);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Inscrits saveInscrits(Inscrits Inscrits) {
		return InscritsRepository.save(Inscrits);
	}
	   
}
