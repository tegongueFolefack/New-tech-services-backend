package com.example.securingweb.Models;


import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.NoteProduitDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class NoteProduit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int Id;
	
	private Double note;
	
	public NoteProduitDTO toNoteProduitDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, NoteProduitDTO.class);
    }

	public NoteProduit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoteProduit(Double note) {
		super();
		this.note = note;
	}
	

}
