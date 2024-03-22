package com.example.securingweb.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Formations;
import com.example.securingweb.Models.Formations;
import com.example.securingweb.Repository.FormationsRepository;



@Service
public class FormationsService {
	
	@Autowired
    private FormationsRepository FormationsRepository;
 

	public Optional<Formations> getFormationsById(Integer id) {
		return FormationsRepository.findById(id);
	}

	public Iterable<Formations> getAllFormations() {
		return FormationsRepository.findAll();
	}

	public boolean deleteFormations(Integer id) {
		Optional<Formations> FormationsOpt = getFormationsById(id);
		if (FormationsOpt.isPresent()) {
			FormationsRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Formations updateFormations(Integer id, Formations Formations2) {
	    Optional<Formations> FormationsOpt = FormationsRepository.findById(id);
	    
	    if (FormationsOpt.isPresent()) {
	        Formations Formations = FormationsOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Formations avec les données du Formations2
	        Formations.setTitre(Formations2.getTitre());
	        Formations.setDescription(Formations2.getDescription());
	        Formations.setDuree(Formations2.getDuree());
	        Formations.setPrix(Formations2.getPrix());
	       
	        
	     // Mettre à jour la date de modification avec la date actuelle
	        Formations.setDateDebut(new Date()); // Utilisez LocalDateTime.now() pour obtenir la date actuelle
	        
	        // Sauvegarder les modifications dans la base de données
	        return FormationsRepository.save(Formations);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Formations saveFormations(Formations Formations) {
		return FormationsRepository.save(Formations);
	}


}
