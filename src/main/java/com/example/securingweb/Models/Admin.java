package com.example.securingweb.Models;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;


import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;

import com.example.securingweb.DTO.AdminDTO;
import com.example.securingweb.Enums.Role;
import com.example.securingweb.Request.RegisterRequest;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;







@Data
//@AllArgsConstructor
@Entity
@Table(name = "admin")
@DiscriminatorValue("admin")


public class Admin extends User{

	private static final long serialVersionUID = 1L;
  
	@OneToMany (mappedBy="admin")
	private List<Formations> Formations =new ArrayList<>();
	
	@OneToMany(mappedBy = "admin")
	private List<Realisations>Realisations = new ArrayList<>();
	
	@OneToMany(mappedBy = "admin")
	private List<Blog>Blog = new ArrayList<>();
	
	
	
	

	public AdminDTO toAdminDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, AdminDTO.class);
    }





	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Admin(String nom, String prenom, String email, String password, String login, Role role) {
		super(nom, prenom, email, password, login, role);
		// TODO Auto-generated constructor stub
	}





	










	




	


	


	

	

	
	

}
