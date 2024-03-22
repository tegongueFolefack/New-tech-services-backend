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

import com.example.securingweb.DTO.FormationsDTO;
import com.example.securingweb.DTO.FormationsDTO;
import com.example.securingweb.Models.Formations;
import com.example.securingweb.Models.Formations;
import com.example.securingweb.Service.FormationsService;
import com.example.securingweb.Service.ImageService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/formation")
public class FormationsController {
	
	@Autowired
	private FormationsService FormationsService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<FormationsDTO> getFormationsById(@PathVariable Integer id) {
        try {
            Optional<Formations>Formations =FormationsService.getFormationsById(id);
            if (Formations.isPresent()) {
            	FormationsDTO FormationsDTO =Formations.get().toFormationsDTO();
                return ResponseEntity.ok(FormationsDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Formations not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteFormations(@PathVariable Integer id) {
        try {
        	FormationsService.deleteFormations(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<List<FormationsDTO>> findAll() {
        try {
            Iterable<Formations>Formationss =FormationsService.getAllFormations();
            List<FormationsDTO>FormationsDTOs = new ArrayList<>();
            for (Formations Formations:Formationss) {
            	FormationsDTOs.add(Formations.toFormationsDTO());
            }
            return ResponseEntity.ok(FormationsDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("/add")
    public ResponseEntity<String> addFormations(@RequestBody FormationsDTO FormationsDto) {
       Formations Formations =FormationsDto.toFormations();
       Formations savedFormations =FormationsService.saveFormations(Formations);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Formations with ID " + savedFormations.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<FormationsDTO> updateFormations(@PathVariable Integer id, @RequestBody FormationsDTO FormationsDto) {
	        try {
	            Optional<Formations>FormationsOpt =FormationsService.getFormationsById(id);
	            if (FormationsOpt.isPresent()) {
	            	Formations Formations =FormationsOpt.get();
	                
	            	Formations =FormationsDto.toFormations();
	                
	            	Formations =FormationsService.updateFormations(id,Formations);
	                
	               FormationsDTO FormationsResponse =Formations.toFormationsDTO();
	                
	                return ResponseEntity.ok(FormationsResponse);
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
