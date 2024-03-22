package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.*;

import com.example.securingweb.Repository.*;


@Service
public class LikeService {
	
	@Autowired
    private LikeRepository LikeRepository;
 

	public Optional<Like> getLikeById(Integer id) {
		return LikeRepository.findById(id);
	}

	public Iterable<Like> getAllLike() {
		return LikeRepository.findAll();
	}

	public boolean deleteLike(Integer id) {
		Optional<Like> LikeOpt = getLikeById(id);
		if (LikeOpt.isPresent()) {
			LikeRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Like updateLike(Integer id, Like Like2) {
	    Optional<Like> LikeOpt = LikeRepository.findById(id);
	    
	    if (LikeOpt.isPresent()) {
	    	Like Like = LikeOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Like.setNombre(Like2.getNombre());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return LikeRepository.save(Like);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Like saveLike(Like Like) {
		return LikeRepository.save(Like);
	}
	
 
}
