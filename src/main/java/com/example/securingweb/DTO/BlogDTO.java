package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;


import com.example.securingweb.Models.Blog;

import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class BlogDTO {
	
	private String titre;
	private String contenu;
	
	 @Temporal(TemporalType.TIMESTAMP)
		private Date datePublication;
	    
	    @Temporal(TemporalType.TIMESTAMP)
		private Date dateModification;
	    
	    @PrePersist
	    protected void onCreate() {
		 datePublication = new Date();
		 dateModification = new Date();
	    }
	
	public Blog toBlog() {
	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper.map(this, Blog.class);
	}
}
