package com.example.securingweb.Service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Commentaire;
import com.example.securingweb.Models.Commentaire;
import com.example.securingweb.Repository.CommentaireRepository;

@Service
public class CommentaireService {
	
	
	@Autowired
    private CommentaireRepository CommentaireRepository;
 

	public Optional<Commentaire> getCommentaireById(Integer id) {
		return CommentaireRepository.findById(id);
	}

	public Iterable<Commentaire> getAllCommentaire() {
		return CommentaireRepository.findAll();
	}

	public boolean deleteCommentaire(Integer id) {
		Optional<Commentaire> CommentaireOpt = getCommentaireById(id);
		if (CommentaireOpt.isPresent()) {
			CommentaireRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Commentaire updateCommentaire(Integer id, Commentaire Commentaire2) {
	    Optional<Commentaire> CommentaireOpt = CommentaireRepository.findById(id);
	    
	    if (CommentaireOpt.isPresent()) {
	        Commentaire Commentaire = CommentaireOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Commentaire avec les données du Commentaire2
	        Commentaire.setEmail(Commentaire2.getEmail());
	        Commentaire.setContenu(Commentaire2.getContenu());
	       
	        
	     // Mettre à jour la date de modification avec la date actuelle
	        Commentaire.setDateEnvoie(new Date()); // Utilisez LocalDateTime.now() pour obtenir la date actuelle
	        
	        // Sauvegarder les modifications dans la base de données
	        return CommentaireRepository.save(Commentaire);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}
	
	
 
 public Commentaire saveCommentaire(Commentaire Commentaire) {
		return CommentaireRepository.save(Commentaire);
	}
}

	

	

	

	
	
	
