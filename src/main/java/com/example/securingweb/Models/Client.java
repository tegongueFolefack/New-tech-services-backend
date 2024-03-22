package com.example.securingweb.Models;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;

import com.example.securingweb.DTO.ClientDTO;
import com.example.securingweb.Enums.Role;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "client")
@DiscriminatorValue("client")
public class Client extends User {
	
	


	private static final long serialVersionUID = 1L;

//	private int telephone;
//	private String adresse;

	@ManyToMany
	private Collection<Panier>  panier = new ArrayList<>();
	
	


	
	public ClientDTO toClientDTO() {
	        ModelMapper modelMapper = new ModelMapper();
	        return modelMapper.map(this, ClientDTO.class);
	    }





	public Client( String nom, String prenom, String email, String password, String login, Role role) {
		super(nom, prenom, email, password, login, role);
		// TODO Auto-generated constructor stub
	}




	public Client() {
		super();
	}



}
