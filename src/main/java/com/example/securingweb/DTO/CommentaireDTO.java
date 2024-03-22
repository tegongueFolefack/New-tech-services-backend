package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Commentaire;


import lombok.Data;

@Data
public class CommentaireDTO {

	private String email;
	private String contenu;
	private Date dateEnvoie;
	
	public Commentaire toCommentaire() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Commentaire.class);
    }
}
