package com.example.securingweb.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.securingweb.Models.Categorie;

import lombok.AllArgsConstructor;
import lombok.Data;






@Data
//@AllArgsConstructor
public class CategorieDTO {
	
	private String intitule;
	
	public Categorie toCategorie() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Categorie.class);
    }
}
