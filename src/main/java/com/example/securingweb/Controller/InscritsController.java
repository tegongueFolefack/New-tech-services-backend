package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.example.securingweb.DTO.InscritsDTO;

import com.example.securingweb.DTO.UserDTO;
import com.example.securingweb.Models.Inscrits;

import com.example.securingweb.Models.User;
import com.example.securingweb.Service.InscritsService;
import com.example.securingweb.Service.InscritsService;
import com.example.securingweb.Service.UtilisateurService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/Inscrits")
public class InscritsController {
	
	@Autowired
	private InscritsService InscritsService;
	
	@GetMapping("/{id}")
    public ResponseEntity<InscritsDTO> getRoleById(@PathVariable Integer id) {
        try {
            Optional<Inscrits> Inscrits = InscritsService.getInscritsById(id);
            if (Inscrits.isPresent()) {
            	InscritsDTO InscritsDTO = Inscrits.get().toInscritsDTO();
                return ResponseEntity.ok(InscritsDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrits not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteInscrits(@PathVariable Integer id) {
        try {
        	InscritsService.deleteInscrits(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<InscritsDTO>> findAll() {
        try {
            Iterable<Inscrits> Inscritss = InscritsService.getAllInscrits();
            List<InscritsDTO> InscritsDTOs = new ArrayList<>();
            for (Inscrits Inscrits: Inscritss) {
            	InscritsDTOs.add(Inscrits.toInscritsDTO());
            }
            return ResponseEntity.ok(InscritsDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("/add")
    public ResponseEntity<String> InscritsRole(@RequestBody InscritsDTO InscritsDto) {
    	Inscrits Inscrits = InscritsDto.toInscrits();
    	Inscrits savedInscrits = InscritsService.saveInscrits(Inscrits);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Inscrits with ID " + savedInscrits.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<InscritsDTO> updateInscrits(@PathVariable Integer id, @RequestBody InscritsDTO InscritsDto) {
	        try {
	            Optional<Inscrits> InscritsOpt = InscritsService.getInscritsById(id);
	            if (InscritsOpt.isPresent()) {
	            	Inscrits Inscrits = InscritsOpt.get();
	                
	            	Inscrits =InscritsDto.toInscrits();
	                
	            	Inscrits = InscritsService.updateInscrits(id, Inscrits);
	                
	            	InscritsDTO InscritsResponse = Inscrits.toInscritsDTO();
	                
	                return ResponseEntity.ok(InscritsResponse);
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
