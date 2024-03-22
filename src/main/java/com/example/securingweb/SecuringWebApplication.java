package com.example.securingweb;

import org.modelmapper.ModelMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;




//import com.example.GestionEmployes.Services.BDInitService;

@SpringBootApplication
public class SecuringWebApplication   implements CommandLineRunner {
	
	
	@Bean
	public ModelMapper modelMapper () {
		return new ModelMapper();
	} 

	public static void main(String[] args) {
		SpringApplication.run(SecuringWebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	

}
