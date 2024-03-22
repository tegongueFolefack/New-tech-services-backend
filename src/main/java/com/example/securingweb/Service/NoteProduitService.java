package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.NoteProduit;
import com.example.securingweb.Repository.NoteProduitRepository;


@Service
public class NoteProduitService {
	
	@Autowired
    private NoteProduitRepository NoteProduitRepository;
 

	public Optional<NoteProduit> getNoteProduitById(Integer id) {
		return NoteProduitRepository.findById(id);
	}

	public Iterable<NoteProduit> getAllNoteProduit() {
		return NoteProduitRepository.findAll();
	}

	public boolean deleteNoteProduit(Integer id) {
		Optional<NoteProduit> NoteProduitOpt = getNoteProduitById(id);
		if (NoteProduitOpt.isPresent()) {
			NoteProduitRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public NoteProduit updateNoteProduit(Integer id, NoteProduit NoteProduit2) {
	    Optional<NoteProduit> NoteProduitOpt = NoteProduitRepository.findById(id);
	    
	    if (NoteProduitOpt.isPresent()) {
	    	NoteProduit NoteProduit = NoteProduitOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	NoteProduit.setNote(NoteProduit2.getNote());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return NoteProduitRepository.save(NoteProduit);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public NoteProduit saveNoteProduit(NoteProduit NoteProduit) {
		return NoteProduitRepository.save(NoteProduit);
	}

}
