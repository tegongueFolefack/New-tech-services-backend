package com.example.securingweb.Models;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.ContactDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private int id;
	private String nom;
	private String email;
	private String contenu;

	
	public  ContactDTO toContactDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ContactDTO.class);
    }


	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Contact(int id, String nom, String email, String contenu) {
		super();
		this.id = id;
		this.nom = nom;
		this.email = email;
		this.contenu = contenu;
	}
	
	
}
