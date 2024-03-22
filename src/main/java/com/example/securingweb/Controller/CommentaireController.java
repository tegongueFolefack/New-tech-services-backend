package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.DTO.CommentaireDTO;
import com.example.securingweb.Models.Commentaire;
import com.example.securingweb.Service.CommentaireService;
import com.example.securingweb.Service.CommentaireService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/Commentaire")
@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
public class CommentaireController {
	
	@Autowired
	private CommentaireService CommentaireService;
	
	@GetMapping("/{id}")
    public ResponseEntity<CommentaireDTO> getCommentaireById(@PathVariable Integer id) {
        try {
            Optional<Commentaire> Commentaire = CommentaireService.getCommentaireById(id);
            if (Commentaire.isPresent()) {
            	CommentaireDTO CommentaireDTO = Commentaire.get().toCommentaireDTO();
                return ResponseEntity.ok(CommentaireDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commentaire not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Integer id) {
        try {
        	CommentaireService.deleteCommentaire(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<CommentaireDTO>> findAll() {
        try {
            Iterable<Commentaire> Commentaires = CommentaireService.getAllCommentaire();
            List<CommentaireDTO> CommentaireDTOs = new ArrayList<>();
            for (Commentaire Commentaire: Commentaires) {
            	CommentaireDTOs.add(Commentaire.toCommentaireDTO());
            }
            return ResponseEntity.ok(CommentaireDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("/add")
    public ResponseEntity<String> addCommentaire(@RequestBody CommentaireDTO CommentaireDto) {
        Commentaire Commentaire = CommentaireDto.toCommentaire();
        Commentaire savedCommentaire = CommentaireService.saveCommentaire(Commentaire);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Commentaire with ID " + savedCommentaire.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<CommentaireDTO> updateCommentaire(@PathVariable Integer id, @RequestBody CommentaireDTO CommentaireDto) {
	        try {
	            Optional<Commentaire> CommentaireOpt = CommentaireService.getCommentaireById(id);
	            if (CommentaireOpt.isPresent()) {
	            	Commentaire Commentaire = CommentaireOpt.get();
	                
	            	Commentaire = CommentaireDto.toCommentaire();
	                
	            	Commentaire = CommentaireService.updateCommentaire(id, Commentaire);
	                
	                CommentaireDTO CommentaireResponse = Commentaire.toCommentaireDTO();
	                
	                return ResponseEntity.ok(CommentaireResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    
	    	
	    @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
	        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"+ ex.getMessage());
	    }


}
