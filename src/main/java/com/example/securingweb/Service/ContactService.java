package com.example.securingweb.Service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Contact;
import com.example.securingweb.Repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
    private ContactRepository ContactRepository;
 

	public Optional<Contact> getContactById(Integer id) {
		return ContactRepository.findById(id);
	}

	public Iterable<Contact> getAllContact() {
		return ContactRepository.findAll();
	}

	public boolean deleteContact(Integer id) {
		Optional<Contact> ContactOpt = getContactById(id);
		if (ContactOpt.isPresent()) {
			ContactRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Contact updateContact(Integer id, Contact Contact2) {
	    Optional<Contact> ContactOpt = ContactRepository.findById(id);
	    
	    if (ContactOpt.isPresent()) {
	        Contact Contact = ContactOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Contact avec les données du Contact2
	        Contact.setNom(Contact2.getNom());
	        Contact.setEmail(Contact2.getEmail());
	        Contact.setContenu(Contact2.getContenu());
	       
	        
	     
	        
	        // Sauvegarder les modifications dans la base de données
	        return ContactRepository.save(Contact);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Contact saveContact(Contact Contact) {
	 
		return ContactRepository.save(Contact);
	}

}
