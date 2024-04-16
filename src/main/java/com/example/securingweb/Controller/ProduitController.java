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

import com.example.securingweb.DTO.ProduitDTO;
import com.example.securingweb.Models.Categorie;
import com.example.securingweb.Models.Image;
import com.example.securingweb.Models.Marque;
import com.example.securingweb.Models.Params;
import com.example.securingweb.Models.Produit;
import com.example.securingweb.Service.CategorieService;
import com.example.securingweb.Service.MarqueService;
import com.example.securingweb.Service.ProduitService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations Produit login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/Produit")
@CrossOrigin(origins = "http://localhost:3000")
//@PreAuthorize("hasRole('ADMIN')")
public class ProduitController {
	

		
		@Autowired
		private ProduitService ProduitService;
		
		@GetMapping("/images/")
		public ResponseEntity<List<Image>> getImagesById() {
		    try {
		        List<Image> images = ProduitService.getImagesById();
		        return ResponseEntity.ok().body(images);
		    } catch (Exception e) {
		        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching images by category ID: " + e.getMessage());
		    }
		}
		
		@GetMapping("/marque/{marqueId}")
		public ResponseEntity<List<Image>> getImagesByMarqueId(@PathVariable Integer marqueId) {
		    try {
		        List<Image> images = ProduitService.getImagesByMarqueId(marqueId);
		        return ResponseEntity.ok().body(images);
		    } catch (Exception e) {
		        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching images by category ID: " + e.getMessage());
		    }
		}
		
		@GetMapping("/categorie/{categoryId}")
		public ResponseEntity<List<Image>> getImagesByCategoryId(@PathVariable Integer categoryId) {
		    try {
		        List<Image> images = ProduitService.getImagesByCategoryId(categoryId);
		        return ResponseEntity.ok().body(images);
		    } catch (Exception e) {
		        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching images by category ID: " + e.getMessage());
		    }
		}


		 @PostMapping("/add-category-brand")
		    public String addCategoryAndBrandToProduct(@RequestBody Params params) {
		        return this.ProduitService.addCategoryAndBrandToProduct(params);
		    }
		
		@GetMapping("/{id}")
		//@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
	    public ResponseEntity<ProduitDTO> getProduitById(@PathVariable Integer id) {
	        try {
	            Optional<Produit> Produit = ProduitService.getProduitById(id);
	            if (Produit.isPresent()) {
	            	ProduitDTO ProduitDTO = Produit.get().toProduitDTO();
	                return ResponseEntity.ok(ProduitDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @DeleteMapping("delete/{id}")
	   // @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> deleteProduit(@PathVariable Integer id) {
	        try {
	        	ProduitService.deleteProduit(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @GetMapping("/")
	    //@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
	    public ResponseEntity<List<ProduitDTO>> findAll() {
	        try {
	            Iterable<Produit> Produits = ProduitService.getAllProduit();
	            List<ProduitDTO> ProduitDTOs = new ArrayList<>();
	            for (Produit Produit: Produits) {
	            	ProduitDTOs.add(Produit.toProduitDTO());
	            }
	            return ResponseEntity.ok(ProduitDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

		   

		
	    @PostMapping("/add")
	    //@PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<String> addProduit(@RequestBody ProduitDTO ProduitDto) {
	    	Produit Produit = ProduitDto.toProduit();
	    	Produit savedProduit = ProduitService.saveProduit(Produit);
	        
	        // You can customize the confirmation message here
	        String confirmationMessage = "Produit with ID " + savedProduit.getId() + " has been added successfully.";
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
	    }

		
		    @PutMapping("update/{id}")
		  //  @PreAuthorize("hasRole('ADMIN')")
		    public ResponseEntity<ProduitDTO> updateFonction(@PathVariable Integer id, @RequestBody ProduitDTO ProduitDto) {
		        try {
		            Optional<Produit> ProduitOpt = ProduitService.getProduitById(id);
		            if (ProduitOpt.isPresent()) {
		            	Produit Produit =ProduitOpt.get();
		                
		            	Produit = ProduitDto.toProduit();
		                
		            	Produit = ProduitService.updateProduit(id, Produit);
		                
		            	ProduitDTO ProduitResponse = Produit.toProduitDTO();
		                
		                return ResponseEntity.ok(ProduitResponse);
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
