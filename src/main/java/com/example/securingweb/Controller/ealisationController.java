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

import com.example.securingweb.DTO.RealisationDTO;
import com.example.securingweb.DTO.RealisationDTO;
import com.example.securingweb.Models.Realisations;
import com.example.securingweb.Service.RealisationsService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations Realisations login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/Realisations")

public class ealisationController {
	
		@Autowired
		private RealisationsService RealisationsService;
		
		@GetMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
	    public ResponseEntity<RealisationDTO> getRealisationsById(@PathVariable Integer id) {
	        try {
	            Optional<Realisations> Realisations = RealisationsService.getRealisationsById(id);
	            if (Realisations.isPresent()) {
	            	RealisationDTO RealisationDTO = Realisations.get().toRealisationDTO();
	                return ResponseEntity.ok(RealisationDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Realisations not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @DeleteMapping("delete/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> deleteRealisations(@PathVariable Integer id) {
	        try {
	        	RealisationsService.deleteRealisations(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @GetMapping("/")
	    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
	    public ResponseEntity<List<RealisationDTO>> findAll() {
	        try {
	            Iterable<Realisations> Realisationss = RealisationsService.getAllRealisations();
	            List<RealisationDTO> RealisationDTOs = new ArrayList<>();
	            for (Realisations Realisations: Realisationss) {
	            	RealisationDTOs.add(Realisations.toRealisationDTO());
	            }
	            return ResponseEntity.ok(RealisationDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

		   

		
	    @PostMapping("/add")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<String> addRealisations(@RequestBody RealisationDTO RealisationDTO) {
	    	Realisations Realisations = RealisationDTO.toRealisation();
	    	Realisations savedRealisations = RealisationsService.saveRealisations(Realisations);
	        
	        // You can customize the confirmation message here
	        String confirmationMessage = "Realisations with ID " + savedRealisations.getId() + " has been added successfully.";
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
	    }

		
		    @PutMapping("update/{id}")
		    @PreAuthorize("hasRole('ADMIN')")
		    public ResponseEntity<RealisationDTO> updateFonction(@PathVariable Integer id, @RequestBody RealisationDTO RealisationDTO) {
		        try {
		            Optional<Realisations> RealisationsOpt = RealisationsService.getRealisationsById(id);
		            if (RealisationsOpt.isPresent()) {
		            	Realisations Realisations =RealisationsOpt.get();
		                
		            	Realisations = RealisationDTO.toRealisation();
		                
		            	Realisations = RealisationsService.updateRealisations(id, Realisations);
		                
		            	RealisationDTO RealisationsResponse = Realisations.toRealisationDTO();
		                
		                return ResponseEntity.ok(RealisationsResponse);
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
