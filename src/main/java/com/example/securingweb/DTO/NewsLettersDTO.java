package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Marque;
import com.example.securingweb.Models.NewsLetters;

import lombok.Data;

@Data
public class NewsLettersDTO {

	
	private String contenu;
	private Date datePublication;
	
	public NewsLetters toNewsLetters() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, NewsLetters.class);
    }
}
