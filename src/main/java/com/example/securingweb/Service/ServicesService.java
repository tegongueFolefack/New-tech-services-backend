package com.example.securingweb.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Image;
import com.example.securingweb.Models.Services;
import com.example.securingweb.Repository.ImageRepository;
import com.example.securingweb.Repository.ServicesRepository;

@Service
public class ServicesService {
	
	@Autowired
    private ServicesRepository ServicesRepository;
	

	public Optional<Services> getServicesById(Integer id) {
		return ServicesRepository.findById(id);
	}

	public Iterable<Services> getAllServices() {
		return ServicesRepository.findAll();
	}

	public boolean deleteServices(Integer id) {
		Optional<Services> ServicesOpt = getServicesById(id);
		if (ServicesOpt.isPresent()) {
			ServicesRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Services updateServices(Integer id, Services Services2) {
	    Optional<Services> ServicesOpt = ServicesRepository.findById(id);
	    
	    if (ServicesOpt.isPresent()) {
	    	Services Services = ServicesOpt.get();
	    	
	        
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Services.setNom(Services2.getNom());
	    	Services.setDescription(Services2.getDescription());
	    	
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return ServicesRepository.save(Services);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Services saveServices(Services Services) {
		return ServicesRepository.save(Services);
	}


}
