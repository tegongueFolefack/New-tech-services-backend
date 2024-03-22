package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Categorie;
import com.example.securingweb.Repository.CategorieRepository;

@Service
public class CategorieService {
	
	 @Autowired
	    private CategorieRepository CategorieRepository;
	 

		public Optional<Categorie> getCategorieById(Integer id) {
			return CategorieRepository.findById(id);
		}

		public Iterable<Categorie> getAllCategorie() {
			return CategorieRepository.findAll();
		}

		public boolean deleteCategorie(Integer id) {
			Optional<Categorie> CategorieOpt = getCategorieById(id);
			if (CategorieOpt.isPresent()) {
				CategorieRepository.deleteById(id);
				return true;
			} else {
				return false;
			}
		}
		
		public Categorie updateCategorie(Integer id, Categorie Categorie2) {
		    Optional<Categorie> CategorieOpt = CategorieRepository.findById(id);
		    
		    if (CategorieOpt.isPresent()) {
		    	Categorie Categorie = CategorieOpt.get();
		        
		        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
		    	Categorie.setIntitule(Categorie2.getIntitule());
		    	
		        
		        // Sauvegarder les modifications dans la base de données
		        return CategorieRepository.save(Categorie);
		    } else {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		    }
		}

	 
	 public Categorie saveCategorie(Categorie Categorie) {
			return CategorieRepository.save(Categorie);
		}

}
