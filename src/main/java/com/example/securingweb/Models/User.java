package com.example.securingweb.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.example.securingweb.DTO.UserDTO;
import com.example.securingweb.Enums.Role;

import java.util.Collection;
import java.util.Date;
import java.util.List;
 
@Builder
@AllArgsConstructor
@Data
@Entity
@Table(name = "_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE" )
public class User implements UserDetails {
	 
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private String nom;
	  private String prenom;
	  private String email;
	  private String password;
	  private String login;
		
		
	
	public UserDTO toUtilisateurDTO() {
	        ModelMapper modelMapper = new ModelMapper();
	        return modelMapper.map(this, UserDTO.class);
	    }

	 
	  @Enumerated(EnumType.STRING)
	  private Role role;
	 
	  @Override
	  public Collection<? extends GrantedAuthority> getAuthorities() {
	    return role.getAuthorities();
	 }
	 
	  @Override
	  public String getPassword() {
	    return password;
	 }
	 
	  @Override
	  public String getUsername() {
	    return email;
	 }
	 
	  @Override
	  public boolean isAccountNonExpired() {
	    return true;
	 }
	 
	  @Override
	  public boolean isAccountNonLocked() {
	    return true;
	 }
	 
	  @Override
	  public boolean isCredentialsNonExpired() {
	    return true;
	 }
	 
	  @Override
	  public boolean isEnabled() {
	    return true;
	 }

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String nom, String prenom, String email, String password, String login, Role role) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.login = login;
		this.role = role;
	}

	
	

	

	
	
	  

}
