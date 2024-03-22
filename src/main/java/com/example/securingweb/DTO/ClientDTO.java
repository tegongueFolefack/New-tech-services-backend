package com.example.securingweb.DTO;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Client;






public class ClientDTO extends UserDTO {
	
	
	public Client toClient() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Client.class);
    }

}
