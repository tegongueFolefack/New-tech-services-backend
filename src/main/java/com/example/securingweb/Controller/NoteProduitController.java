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

import com.example.securingweb.DTO.NoteProduitDTO;
import com.example.securingweb.Models.NoteProduit;
import com.example.securingweb.Service.NoteProduitService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations NoteProduit login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/NoteProduit")
@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
public class NoteProduitController {
	
	
			@Autowired
			private NoteProduitService NoteProduitService;
			
			@GetMapping("/{id}")
		    public ResponseEntity<NoteProduitDTO> getNoteProduitById(@PathVariable Integer id) {
		        try {
		            Optional<NoteProduit> NoteProduit = NoteProduitService.getNoteProduitById(id);
		            if (NoteProduit.isPresent()) {
		            	NoteProduitDTO NoteProduitDTO = NoteProduit.get().toNoteProduitDTO();
		                return ResponseEntity.ok(NoteProduitDTO);
		            } else {
		                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NoteProduit not found with ID: " + id);
		            }
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

		    @DeleteMapping("delete/{id}")
		    public ResponseEntity<Void> deleteNoteProduit(@PathVariable Integer id) {
		        try {
		        	NoteProduitService.deleteNoteProduit(id);
		            return ResponseEntity.noContent().build();
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

		    @GetMapping("/")
		    public ResponseEntity<List<NoteProduitDTO>> findAll() {
		        try {
		            Iterable<NoteProduit> NoteProduits = NoteProduitService.getAllNoteProduit();
		            List<NoteProduitDTO> NoteProduitDTOs = new ArrayList<>();
		            for (NoteProduit NoteProduit: NoteProduits) {
		            	NoteProduitDTOs.add(NoteProduit.toNoteProduitDTO());
		            }
		            return ResponseEntity.ok(NoteProduitDTOs);
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

			   

			
		    @PostMapping("/add")
		    public ResponseEntity<String> addNoteProduit(@RequestBody NoteProduitDTO NoteProduitDto) {
		    	NoteProduit NoteProduit = NoteProduitDto.toNoteProduit();
		    	NoteProduit savedNoteProduit = NoteProduitService.saveNoteProduit(NoteProduit);
		        
		        // You can customize the confirmation message here
		        String confirmationMessage = "NoteProduit with ID " + savedNoteProduit.getId() + " has been added successfully.";
		        
		        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
		    }

			
			    @PutMapping("update/{id}")
			    public ResponseEntity<NoteProduitDTO> updateFonction(@PathVariable Integer id, @RequestBody NoteProduitDTO NoteProduitDto) {
			        try {
			            Optional<NoteProduit> NoteProduitOpt = NoteProduitService.getNoteProduitById(id);
			            if (NoteProduitOpt.isPresent()) {
			            	NoteProduit NoteProduit =NoteProduitOpt.get();
			                
			            	NoteProduit = NoteProduitDto.toNoteProduit();
			                
			            	NoteProduit = NoteProduitService.updateNoteProduit(id, NoteProduit);
			                
			            	NoteProduitDTO NoteProduitResponse = NoteProduit.toNoteProduitDTO();
			                
			                return ResponseEntity.ok(NoteProduitResponse);
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
			        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"+ex.getMessage());
			    }


}
