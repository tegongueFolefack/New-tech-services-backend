package com.example.securingweb.Models;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.ServicesDTO;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "_service")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE" )
public class Services {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nom;
	private String description;
	
	@OneToMany
	private List<Image> images = new ArrayList<>();

	public Services() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ServicesDTO toServicesDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ServicesDTO.class);
    }

	public Services(String nom, String description) {
		super();
		this.nom = nom;
		this.description = description;
	}

	
	
}
