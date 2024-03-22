package com.example.securingweb.Service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Reduction;
import com.example.securingweb.Models.Reduction;
import com.example.securingweb.Repository.ReductionRepository;

@Service
public class ReductionService {
	
	@Autowired
    private ReductionRepository ReductionRepository;
 

	public Optional<Reduction> getReductionById(Integer id) {
		return ReductionRepository.findById(id);
	}

	public Iterable<Reduction> getAllReduction() {
		return ReductionRepository.findAll();
	}

	public boolean deleteReduction(Integer id) {
		Optional<Reduction> ReductionOpt = getReductionById(id);
		if (ReductionOpt.isPresent()) {
			ReductionRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	

	public Reduction updateReduction(Integer id, Reduction Reduction2) {
	    Optional<Reduction> ReductionOpt = ReductionRepository.findById(id);
	    
	    if (ReductionOpt.isPresent()) {
	        Reduction Reduction = ReductionOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Reduction avec les données du Reduction2
	        Reduction.setMontant(Reduction2.getMontant());
	    	Reduction.setDuree(Reduction2.getDuree());
	       
	        
	     // Mettre à jour la date de modification avec la date actuelle
	        Reduction.setDateDebut(new Date()); // Utilisez LocalDateTime.now() pour obtenir la date actuelle
	        
	        // Sauvegarder les modifications dans la base de données
	        return ReductionRepository.save(Reduction);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}
 
 public Reduction saveReduction(Reduction Reduction) {
		return ReductionRepository.save(Reduction);
	}


}
