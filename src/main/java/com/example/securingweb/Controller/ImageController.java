package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.DTO.ImageDTO;
import com.example.securingweb.Models.Image;
import com.example.securingweb.Models.ParamsImage;
import com.example.securingweb.Service.ImageService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;



@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@RestController
@RequestMapping("/Image")
@SecurityRequirements() 
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/byLibelle/{libelle}")
    public ResponseEntity<?> getImagesByLibelle(@PathVariable String libelle) {
        List<Image> images = imageService.getImagesByLibelle(libelle);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
	
//	 @GetMapping("/byLibelle/{libelle}")
//	    public ResponseEntity<?> getImageByLibelle(@PathVariable String libelle) {
//	        Optional<Image> image = imageService.getImageByLibelle(libelle);
//	        if (image.isPresent()) {
//	            return ResponseEntity.ok(image.get());
//	        } else {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found with libelle: " + libelle);
//	        }
//	    }
	
//	 @PostMapping("/add-service")
//	    public String addService(@RequestBody ParamsImage ParamsImage) {
//	        return this.imageService.addService( ParamsImage);
//	    }
	 
		@GetMapping("/{id}")
		//@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
	    public ResponseEntity<ImageDTO> getimageById(@PathVariable Integer id) {
	        try {
	            Optional<Image> image = imageService.getImageById(id);
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
		
		 
		 @GetMapping("/image/{id}")
		    public ResponseEntity<String> getImageAsBase64(@PathVariable Integer id) {
		        try {
		            String imageDataBase64 = imageService.getImageAsBase64(id);
		            return ResponseEntity.ok(imageDataBase64);
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

	    @DeleteMapping("delete/{id}")
	    public ResponseEntity<Void> deleteImage(@PathVariable Integer id) {
	        try {
	        	imageService.deleteImage(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @GetMapping("/")
	    //@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
	    public ResponseEntity<List<ImageDTO>> findAll() {
	        try {
	            Iterable<Image> images = imageService.getAllImage();
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
	    public ResponseEntity<String> addImage(@RequestParam("file") MultipartFile file, @RequestParam("libelle") String libelle) {
	        try {
	            Image image = new Image();
	            image.setLibelle(libelle);
	            
	            // Convertir les données binaires de l'image en tableau d'octets
	            byte[] imageDataBytes = file.getBytes();
	            image.setImageData(imageDataBytes);
	            
	            Image savedImage = imageService.saveImage(image);
	            
	            String confirmationMessage = "Image with ID " + savedImage.getId() + " has been added successfully.";
	            
	            return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while adding the image");
	        }
	    }




		
		    @PutMapping("update/{id}")
		    public ResponseEntity<ImageDTO> updateImage(@PathVariable Integer id, @RequestBody ImageDTO imageDto) {
		        try {
		            Optional<Image> imageOpt = imageService.getImageById(id);
		            if (imageOpt.isPresent()) {
		            	Image image = imageOpt.get();
		                
		            	image = imageDto.toImage();
		                
		            	image = imageService.updateImage(id, image);
		                
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
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred" + ex.getMessage());
		    }

		    
}




