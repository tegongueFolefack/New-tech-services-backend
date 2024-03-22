package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Produit;
import com.example.securingweb.Repository.ProduitRepository;

@Service
public class ProduitService {
	
	@Autowired
    private ProduitRepository ProduitRepository;
 

	public Optional<Produit> getProduitById(Integer id) {
		return ProduitRepository.findById(id);
	}

	public Iterable<Produit> getAllProduit() {
		return ProduitRepository.findAll();
	}

	public boolean deleteProduit(Integer id) {
		Optional<Produit> ProduitOpt = getProduitById(id);
		if (ProduitOpt.isPresent()) {
			ProduitRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Produit updateProduit(Integer id, Produit Produit2) {
	    Optional<Produit> ProduitOpt = ProduitRepository.findById(id);
	    
	    if (ProduitOpt.isPresent()) {
	    	Produit Produit = ProduitOpt.get();
	    	
	        
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Produit.setQuantiteDisponible(Produit2.getQuantiteDisponible());
	    	Produit.setPrixUnitaire(Produit2.getPrixUnitaire());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return ProduitRepository.save(Produit);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Produit saveProduit(Produit Produit) {
		return ProduitRepository.save(Produit);
	}

}
