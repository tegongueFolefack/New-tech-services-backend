package com.example.securingweb.DTO;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Like;


import lombok.Data;

@Data
public class LikeDTO {
	
	private int nombre;
	
	public Like toLike() {
	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper.map(this, Like.class);
	}

}
