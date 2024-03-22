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

import com.example.securingweb.DTO.CategorieDTO;
import com.example.securingweb.Models.Categorie;
import com.example.securingweb.Service.CategorieService;

import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/Categorie")
public class CategorieController {
	
	@Autowired
	private CategorieService CategorieService;
	
	
	 @PostMapping("/add")
	   // @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<String> addCategorie(@RequestBody CategorieDTO adminDto) {
	    	
	    	Categorie admin =adminDto.toCategorie();
	    	 
	    	Categorie savedCategorie = CategorieService.saveCategorie(admin);
	    	
	        
	        // You can customize the confirmation message here
	        String confirmationMessage = "Comptable with ID  has been added successfully.";
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
	    }
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<CategorieDTO> getCategorieById(@PathVariable Integer id) {
        try {
            Optional<Categorie> Categorie = CategorieService.getCategorieById(id);
            if (Categorie.isPresent()) {
                CategorieDTO CategorieDTO = Categorie.get().toCategorieDTO();
                return ResponseEntity.ok(CategorieDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Integer id) {
        try {
            CategorieService.deleteCategorie(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }
    
   
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<List<CategorieDTO>> findAll() {
        try {
            Iterable<Categorie> Categories = CategorieService.getAllCategorie();
            List<CategorieDTO> CategorieDTOs = new ArrayList<>();
            for (Categorie Categorie : Categories) {
                CategorieDTOs.add(Categorie.toCategorieDTO());
            }
            return ResponseEntity.ok(CategorieDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
   

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<CategorieDTO> updateCategorie(@PathVariable Integer id, @RequestBody CategorieDTO CategorieDto) {
	        try {
	            Optional<Categorie> CategorieOpt = CategorieService.getCategorieById(id);
	            if (CategorieOpt.isPresent()) {
	                Categorie Categorie = CategorieOpt.get();
	                
	                Categorie = CategorieDto.toCategorie();
	                
	                Categorie = CategorieService.updateCategorie(id, Categorie);
	                
	                CategorieDTO CategorieResponse = Categorie.toCategorieDTO();
	                
	                return ResponseEntity.ok(CategorieResponse);
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
