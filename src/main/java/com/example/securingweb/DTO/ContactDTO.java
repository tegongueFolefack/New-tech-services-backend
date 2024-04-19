package com.example.securingweb.DTO;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Commentaire;
import com.example.securingweb.Models.Contact;

import lombok.Data;

@Data
public class ContactDTO {
	
	private String nom;
	private String email;
	private String contenu;
	
	public Contact toContact() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Contact.class);
    }

}
