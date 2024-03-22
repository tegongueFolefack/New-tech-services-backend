package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Marque;
import com.example.securingweb.Repository.MarqueRepository;

@Service
public class MarqueService {
	
	@Autowired
    private MarqueRepository MarqueRepository;
 

	public Optional<Marque> getMarqueById(Integer id) {
		return MarqueRepository.findById(id);
	}

	public Iterable<Marque> getAllMarque() {
		return MarqueRepository.findAll();
	}

	public boolean deleteMarque(Integer id) {
		Optional<Marque> MarqueOpt = getMarqueById(id);
		if (MarqueOpt.isPresent()) {
			MarqueRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Marque updateMarque(Integer id, Marque Marque2) {
	    Optional<Marque> MarqueOpt = MarqueRepository.findById(id);
	    
	    if (MarqueOpt.isPresent()) {
	    	Marque Marque = MarqueOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Marque.setLibelle(Marque2.getLibelle());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return MarqueRepository.save(Marque);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Marque saveMarque(Marque Marque) {
		return MarqueRepository.save(Marque);
	}
	

}
