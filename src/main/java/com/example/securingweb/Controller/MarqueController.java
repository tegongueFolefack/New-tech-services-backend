package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.example.securingweb.DTO.MarqueDTO;
import com.example.securingweb.Models.Marque;
import com.example.securingweb.Service.MarqueService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations Marque login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/Marque")
@CrossOrigin(origins = "http://localhost:3000")
//@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
public class MarqueController {
	
	@Autowired
	private MarqueService MarqueService;
	
	@GetMapping("/{id}")
    public ResponseEntity<MarqueDTO> getMarqueById(@PathVariable Integer id) {
        try {
            Optional<Marque> Marque = MarqueService.getMarqueById(id);
            if (Marque.isPresent()) {
            	MarqueDTO MarqueDTO = Marque.get().toMarqueDTO();
                return ResponseEntity.ok(MarqueDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marque not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteMarque(@PathVariable Integer id) {
        try {
        	MarqueService.deleteMarque(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<MarqueDTO>> findAll() {
        try {
            Iterable<Marque> Marques = MarqueService.getAllMarque();
            List<MarqueDTO> MarqueDTOs = new ArrayList<>();
            for (Marque Marque: Marques) {
            	MarqueDTOs.add(Marque.toMarqueDTO());
            }
            return ResponseEntity.ok(MarqueDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("/add")
    public ResponseEntity<String> addMarque(@RequestBody MarqueDTO MarqueDto) {
    	Marque Marque = MarqueDto.toMarque();
    	Marque savedMarque = MarqueService.saveMarque(Marque);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Marque with ID " + savedMarque.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<MarqueDTO> updateFonction(@PathVariable Integer id, @RequestBody MarqueDTO MarqueDto) {
	        try {
	            Optional<Marque> MarqueOpt = MarqueService.getMarqueById(id);
	            if (MarqueOpt.isPresent()) {
	            	Marque Marque =MarqueOpt.get();
	                
	            	Marque = MarqueDto.toMarque();
	                
	            	Marque = MarqueService.updateMarque(id, Marque);
	                
	            	MarqueDTO MarqueResponse = Marque.toMarqueDTO();
	                
	                return ResponseEntity.ok(MarqueResponse);
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
