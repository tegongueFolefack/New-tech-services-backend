package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.*;
import com.example.securingweb.Repository.*;

@Service
public class ImageService {
	
	@Autowired
    private ImageRepository ImageRepository;
 

	public Optional<Image> getImageById(Integer id) {
		return ImageRepository.findById(id);
	}

	public Iterable<Image> getAllImage() {
		return ImageRepository.findAll();
	}

	public boolean deleteImage(Integer id) {
		Optional<Image> ImageOpt = getImageById(id);
		if (ImageOpt.isPresent()) {
			ImageRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Image updateImage(Integer id, Image Image2) {
	    Optional<Image> ImageOpt = ImageRepository.findById(id);
	    
	    if (ImageOpt.isPresent()) {
	    	Image Image = ImageOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Image.setLibelle(Image2.getLibelle());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return ImageRepository.save(Image);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Image saveImage(Image Image) {
		return ImageRepository.save(Image);
	}

}
