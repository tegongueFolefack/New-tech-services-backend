package com.example.securingweb.Models;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.NewsLettersDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class NewsLetters {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int Id;
	
	private String contenu;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datePublication;
    
    @PrePersist
    protected void onCreate() {
	 datePublication = new Date();
    }

	
	
	@OneToMany
	private List<Inscrits> inscrits = new ArrayList<>();
	
	
	public NewsLettersDTO toNewsLettersDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, NewsLettersDTO.class);
    }


	public NewsLetters() {
		super();
		// TODO Auto-generated constructor stub
	}


	public NewsLetters(String contenu, Date datePublication) {
		super();
		this.contenu = contenu;
		this.datePublication = datePublication;
	}
	
	

}
