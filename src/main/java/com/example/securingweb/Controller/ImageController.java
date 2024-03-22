package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
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


import com.example.securingweb.DTO.ImageDTO;

import com.example.securingweb.Models.Image;
import com.example.securingweb.Models.User;
import com.example.securingweb.Repository.UserRepository;
import com.example.securingweb.Service.ImageService;
import com.example.securingweb.Service.InscritsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;



@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@RestController
@RequestMapping("/Image")
@SecurityRequirements() 

public class ImageController {
	
	@Autowired
	private ImageService inscritsService;
	
	
		@GetMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
	    public ResponseEntity<ImageDTO> getimageById(@PathVariable Integer id) {
	        try {
	            Optional<Image> image = inscritsService.getImageById(id);
	            if (image.isPresent()) {
	            	ImageDTO imageDTO = image.get().toImageDTO();
	                return ResponseEntity.ok(imageDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comptable not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
		
		 

	    @DeleteMapping("delete/{id}")
	    public ResponseEntity<Void> deleteImage(@PathVariable Integer id) {
	        try {
	        	inscritsService.deleteImage(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @GetMapping("/")
	    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
	    public ResponseEntity<List<ImageDTO>> findAll() {
	        try {
	            Iterable<Image> images = inscritsService.getAllImage();
	            List<ImageDTO> imageDTOs = new ArrayList<>();
	            for (Image image : images) {
	            	imageDTOs.add(image.toImageDTO());
	            }
	            return ResponseEntity.ok(imageDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

		   

		
	    @PostMapping("/add")
	    public ResponseEntity<String> addImage(@RequestBody ImageDTO imageDTOs) {
	    	Image image = imageDTOs.toImage();
	    	Image savedimage = inscritsService.saveImage(image);
	        
	        // You can customize the confirmation message here
	        String confirmationMessage = "Image with ID " + savedimage.getId() + " has been added successfully.";
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
	    }

		
		    @PutMapping("update/{id}")
		    public ResponseEntity<ImageDTO> updateImage(@PathVariable Integer id, @RequestBody ImageDTO imageDto) {
		        try {
		            Optional<Image> imageOpt = inscritsService.getImageById(id);
		            if (imageOpt.isPresent()) {
		            	Image image = imageOpt.get();
		                
		            	image = imageDto.toImage();
		                
		            	image = inscritsService.updateImage(id, image);
		                
		            	ImageDTO imageResponse = image.toImageDTO();
		                
		                return ResponseEntity.ok(imageResponse);
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
