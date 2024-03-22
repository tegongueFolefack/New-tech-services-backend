package com.example.securingweb.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Livraison;
import com.example.securingweb.Repository.LivraisonRepository;

@Service
public class LivraisonService {
	
	@Autowired
    private LivraisonRepository LivraisonRepository;
 

	public Optional<Livraison> getLivraisonById(Integer id) {
		return LivraisonRepository.findById(id);
	}

	public Iterable<Livraison> getAllLivraison() {
		return LivraisonRepository.findAll();
	}

	public boolean deleteLivraison(Integer id) {
		Optional<Livraison> LivraisonOpt = getLivraisonById(id);
		if (LivraisonOpt.isPresent()) {
			LivraisonRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Livraison updateLivraison(Integer id, Livraison Livraison2) {
	    Optional<Livraison> LivraisonOpt = LivraisonRepository.findById(id);
	    
	    if (LivraisonOpt.isPresent()) {
	    	Livraison Livraison = LivraisonOpt.get();
	    	
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Livraison.setLivrable(Livraison2.getLivrable());
	    	Livraison.setDateLivraison(Livraison2.getDateLivraison());
	    	Livraison.setLieuLivraison(Livraison2.getLieuLivraison());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return LivraisonRepository.save(Livraison);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Livraison saveLivraison(Livraison Livraison) {
		return LivraisonRepository.save(Livraison);
	}
}
