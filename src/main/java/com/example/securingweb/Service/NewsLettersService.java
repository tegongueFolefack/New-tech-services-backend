package com.example.securingweb.Service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.NewsLetters;
import com.example.securingweb.Models.NewsLetters;
import com.example.securingweb.Repository.NewsLettersRepository;

@Service
public class NewsLettersService {

	
	@Autowired
    private NewsLettersRepository NewsLettersRepository;
 

	public Optional<NewsLetters> getNewsLettersById(Integer id) {
		return NewsLettersRepository.findById(id);
	}

	public Iterable<NewsLetters> getAllNewsLetters() {
		return NewsLettersRepository.findAll();
	}

	public boolean deleteNewsLetters(Integer id) {
		Optional<NewsLetters> NewsLettersOpt = getNewsLettersById(id);
		if (NewsLettersOpt.isPresent()) {
			NewsLettersRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	

	public NewsLetters updateNewsLetters(Integer id, NewsLetters NewsLetters2) {
	    Optional<NewsLetters> NewsLettersOpt = NewsLettersRepository.findById(id);
	    
	    if (NewsLettersOpt.isPresent()) {
	        NewsLetters NewsLetters = NewsLettersOpt.get();
	        
	        // Mise à jour des propriétés de l'objet NewsLetters avec les données du NewsLetters2
	        
	    	NewsLetters.setContenu(NewsLetters2.getContenu());
	       
	        
	     // Mettre à jour la date de modification avec la date actuelle // Utilisez LocalDateTime.now() pour obtenir la date actuelle
	        NewsLetters.setDatePublication(new Date()); 
	        // Sauvegarder les modifications dans la base de données
	        return NewsLettersRepository.save(NewsLetters);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}
 
 public NewsLetters saveNewsLetters(NewsLetters NewsLetters) {
		return NewsLettersRepository.save(NewsLetters);
	}
	
}
