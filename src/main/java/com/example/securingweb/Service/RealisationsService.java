package com.example.securingweb.Service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Realisations;
import com.example.securingweb.Models.Categorie;
import com.example.securingweb.Models.Realisations;
import com.example.securingweb.Repository.RealisationRepository;



@Service
public class RealisationsService {
	
	@Autowired
    private RealisationRepository RealisationsRepository;
 

	public Optional<Realisations> getRealisationsById(Integer id) {
		return RealisationsRepository.findById(id);
	}

	public Iterable<Realisations> getAllRealisations() {
		return RealisationsRepository.findAll();
	}

	public boolean deleteRealisations(Integer id) {
		Optional<Realisations> RealisationsOpt = getRealisationsById(id);
		if (RealisationsOpt.isPresent()) {
			RealisationsRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	
	public Realisations updateRealisations(Integer id, Realisations Realisations2) {
	    Optional<Realisations> RealisationsOpt = RealisationsRepository.findById(id);
	    
	    if (RealisationsOpt.isPresent()) {
	        Realisations Realisations = RealisationsOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Realisations avec les données du Realisations2
	        Realisations.setCategorie(Realisations2.getCategorie());
	    	Realisations.setTitre(Realisations2.getTitre());
	    	Realisations.setDescription(Realisations2.getDescription());
	       
	        
	     // Mettre à jour la date de modification avec la date actuelle
	        Realisations.setDateRealisation(new Date()); // Utilisez LocalDateTime.now() pour obtenir la date actuelle
	        
	        // Sauvegarder les modifications dans la base de données
	        return RealisationsRepository.save(Realisations);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}
 
 public Realisations saveRealisations(Realisations Realisations) {
		return RealisationsRepository.save(Realisations);
	}

}
