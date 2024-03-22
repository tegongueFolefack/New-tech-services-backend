package com.example.securingweb.DTO;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Admin;

import jakarta.persistence.OneToMany;




public class AdminDTO extends UserDTO{
	
	
	
	public Admin toAdmin() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Admin.class);
    }
	

}
