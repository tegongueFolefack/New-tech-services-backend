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

import com.example.securingweb.DTO.PanierDTO;
import com.example.securingweb.Models.Panier;
import com.example.securingweb.Service.PanierService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations Panier login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/Panier")
@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
public class PanierController {
	
	
	
		
		@Autowired
		private PanierService PanierService;
		
		@GetMapping("/{id}")
	    public ResponseEntity<PanierDTO> getPanierById(@PathVariable Integer id) {
	        try {
	            Optional<Panier> Panier = PanierService.getPanierById(id);
	            if (Panier.isPresent()) {
	            	PanierDTO PanierDTO = Panier.get().toPanierDTO();
	                return ResponseEntity.ok(PanierDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Panier not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @DeleteMapping("delete/{id}")
	    public ResponseEntity<Void> deletePanier(@PathVariable Integer id) {
	        try {
	        	PanierService.deletePanier(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @GetMapping("/")
	    public ResponseEntity<List<PanierDTO>> findAll() {
	        try {
	            Iterable<Panier> Paniers = PanierService.getAllPanier();
	            List<PanierDTO> PanierDTOs = new ArrayList<>();
	            for (Panier Panier: Paniers) {
	            	PanierDTOs.add(Panier.toPanierDTO());
	            }
	            return ResponseEntity.ok(PanierDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

		   

		
	    @PostMapping("/add")
	    public ResponseEntity<String> addPanier(@RequestBody PanierDTO PanierDto) {
	    	Panier Panier = PanierDto.toPanier();
	    	Panier savedPanier = PanierService.savePanier(Panier);
	        
	        // You can customize the confirmation message here
	        String confirmationMessage = "Panier with ID " + savedPanier.getId() + " has been added successfully.";
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
	    }

		
		    @PutMapping("update/{id}")
		    public ResponseEntity<PanierDTO> updateFonction(@PathVariable Integer id, @RequestBody PanierDTO PanierDto) {
		        try {
		            Optional<Panier> PanierOpt = PanierService.getPanierById(id);
		            if (PanierOpt.isPresent()) {
		            	Panier Panier =PanierOpt.get();
		                
		            	Panier = PanierDto.toPanier();
		                
		            	Panier = PanierService.updatePanier(id, Panier);
		                
		            	PanierDTO PanierResponse = Panier.toPanierDTO();
		                
		                return ResponseEntity.ok(PanierResponse);
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
