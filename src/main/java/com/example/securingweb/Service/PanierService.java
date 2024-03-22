package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Panier;
import com.example.securingweb.Repository.PanierRepository;

@Service
public class PanierService {

	@Autowired
    private PanierRepository PanierRepository;
 

	public Optional<Panier> getPanierById(Integer id) {
		return PanierRepository.findById(id);
	}

	public Iterable<Panier> getAllPanier() {
		return PanierRepository.findAll();
	}

	public boolean deletePanier(Integer id) {
		Optional<Panier> PanierOpt = getPanierById(id);
		if (PanierOpt.isPresent()) {
			PanierRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Panier updatePanier(Integer id, Panier Panier2) {
	    Optional<Panier> PanierOpt = PanierRepository.findById(id);
	    
	    if (PanierOpt.isPresent()) {
	    	Panier Panier = PanierOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Panier.setTotal(Panier2.getTotal());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return PanierRepository.save(Panier);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Panier savePanier(Panier Panier) {
		return PanierRepository.save(Panier);
	}
}
