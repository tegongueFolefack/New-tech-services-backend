package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Reduction;

import lombok.Data;

@Data
public class ReductionDTO {

	
	private Double montant;
	private Date dateDebut;
	private int duree;
	
	public Reduction toProduit() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Reduction.class);
    }
}
