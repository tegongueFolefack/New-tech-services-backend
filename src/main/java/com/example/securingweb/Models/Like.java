package com.example.securingweb.Models;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.LikeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity(name = "likes")
@AllArgsConstructor
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private int id;
	
	@ManyToOne
	private Produit produit;
	
	private int nombre;
	
	
	


	public LikeDTO toLikeDTO() {
		ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, LikeDTO.class);
	}





	public Like() {
		super();
		// TODO Auto-generated constructor stub
	}





	public Like( int nombre) {
		super();
		this.nombre = nombre;
	}


}
