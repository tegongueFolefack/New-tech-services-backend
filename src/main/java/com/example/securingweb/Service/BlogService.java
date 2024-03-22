package com.example.securingweb.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Enums.TokenType;
import com.example.securingweb.Models.Blog;
import com.example.securingweb.Models.User;
import com.example.securingweb.Repository.BlogRepository;
import com.example.securingweb.Repository.UserRepository;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;
 
 
 
@Service @Transactional
@RequiredArgsConstructor
public class BlogService {
	
	@Autowired
    private BlogRepository BlogRepository;
 

	public Optional<Blog> getBlogById(Integer id) {
		return BlogRepository.findById(id);
	}

	public Iterable<Blog> getAllBlog() {
		return BlogRepository.findAll();
	}

	public boolean deleteBlog(Integer id) {
		Optional<Blog> BlogOpt = getBlogById(id);
		if (BlogOpt.isPresent()) {
			BlogRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Blog updateBlog(Integer id, Blog Blog2) {
	    Optional<Blog> BlogOpt = BlogRepository.findById(id);
	    
	    if (BlogOpt.isPresent()) {
	        Blog Blog = BlogOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Blog avec les données du Blog2
	        Blog.setTitre(Blog2.getTitre());
	        Blog.setContenu(Blog2.getContenu());
	       
	        
	     // Mettre à jour la date de modification avec la date actuelle
	        Blog.setDateModification(new Date()); // Utilisez LocalDateTime.now() pour obtenir la date actuelle
	        
	        // Sauvegarder les modifications dans la base de données
	        return BlogRepository.save(Blog);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Blog saveBlog(Blog Blog) {
	 
		return BlogRepository.save(Blog);
	}
}
