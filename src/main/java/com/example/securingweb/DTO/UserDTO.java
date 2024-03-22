package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.User;

import lombok.Data;

@Data
public class UserDTO {
	
	
	 private String nom;
	  private String prenom;
	  private String email;
	  private String password;
	  private String login;
	
	public User toUtilisateur() {
	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper.map(this, User.class);
	}
}

