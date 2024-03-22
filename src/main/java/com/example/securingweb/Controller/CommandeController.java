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

import com.example.securingweb.DTO.*;
import com.example.securingweb.Models.*;
import com.example.securingweb.Service.CommandeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
//@SecurityRequirements()
@RestController
@RequestMapping("/Commande")
@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
public class CommandeController {
	
	@Autowired
	private CommandeService commandeService;
	
	
	
	@GetMapping("/{id}")
    public ResponseEntity<CommandeDTO> getCommandeById(@PathVariable Integer id) {
        try {
            Optional<Commande> Commande = commandeService.getCommandeById(id);
            if (Commande.isPresent()) {
            	CommandeDTO CommandeDTO = Commande.get().toCommandeDTO();
                return ResponseEntity.ok(CommandeDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "admin not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Integer id) {
        try {
        	commandeService.deleteCommande(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<CommandeDTO>> findAll() {
        try {
            Iterable<Commande> Commandes = commandeService.getAllCommande();
            List<CommandeDTO> CommandeDTOs = new ArrayList<>();
            for (Commande Commande : Commandes) {
            	CommandeDTOs.add(Commande.toCommandeDTO());
            }
            return ResponseEntity.ok(CommandeDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("/add")
    public ResponseEntity<String> addCommande(@RequestBody CommandeDTO CommandeDto) {
    	Commande Commande =CommandeDto.toCommande();
    	Commande savedCommande = commandeService.saveCommande(Commande);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Commande with ID " + savedCommande.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	

    @PutMapping("update/{id}")
    public ResponseEntity<CommandeDTO> updateCommande(@PathVariable Integer id, @RequestBody CommandeDTO CommandeDto) {
        try {
            Optional<Commande> CommandeOpt = commandeService.getCommandeById(id);
            if (CommandeOpt.isPresent()) {
            	Commande Commande = CommandeOpt.get();
                
            	Commande = CommandeDto.toCommande();
                
            	Commande = commandeService.updateCommande(id, Commande);
                
            	CommandeDTO CommandeResponse = Commande.toCommandeDTO();
                
                return ResponseEntity.ok(CommandeResponse);
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
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred" + ex.getMessage());
	    }

}
