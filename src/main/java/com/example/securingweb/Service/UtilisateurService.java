package com.example.securingweb.Service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import com.example.securingweb.Models.User;


import com.example.securingweb.Repository.UserRepository;

@Service
public class UtilisateurService {

	
	 @Autowired
	    private UserRepository utilisateurRepository;
	 
	
	 
	 
	 

		public Optional<User> getUtilisateurById(Long id) {
			return utilisateurRepository.findById(id);
		}

		public Iterable<User> getAllUtilisateur() {
			return utilisateurRepository.findAll();
		}

		public boolean deleteUtilisateur(Long id) {
			Optional<User> utilisateurOpt = getUtilisateurById(id);
			if (utilisateurOpt.isPresent()) {
				utilisateurRepository.deleteById(id);
				return true;
			} else {
				return false;
			}
		}
		
		public User updateUtilisateur(Long id, User utilisateur2) {
		    Optional<User> utilisateurOpt = utilisateurRepository.findById(id);
		    
		    if (utilisateurOpt.isPresent()) {
		    	User utilisateur = utilisateurOpt.get();
		        
		        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
		    	utilisateur.setPassword(utilisateur2.getPassword());
		    	utilisateur.setNom(utilisateur2.getNom());
		    	utilisateur.setEmail(utilisateur2.getEmail());

		        
		        // Sauvegarder les modifications dans la base de données
		        return utilisateurRepository.save(utilisateur);
		    } else {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		    }
		}

	 
	 public User saveUtilisateur(User utilisateur) {
			return utilisateurRepository.save(utilisateur);
		}
	 
	 
	
		

}
