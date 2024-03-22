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

import com.example.securingweb.DTO.NewsLettersDTO;
import com.example.securingweb.Models.NewsLetters;
import com.example.securingweb.Service.NewsLettersService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@RestController
@RequestMapping("/NewsLetters")
//@SecurityRequirements()
@RequiredArgsConstructor
public class NewsLetterConroller {
	
	public class NewsLettersController {
		
		@Autowired
		private NewsLettersService NewsLettersService;

		@GetMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<NewsLettersDTO> getNewsLettersById(@PathVariable Integer id) {
			 
	        try {
	        	
	            Optional<NewsLetters> NewsLetters = NewsLettersService.getNewsLettersById(id);
	            System.out.println("shgshgshdgadhs");
	            if (NewsLetters.isPresent()) {
	            	
	            	NewsLettersDTO NewsLettersDTO = NewsLetters.get().toNewsLettersDTO();
	            
	                return ResponseEntity.ok(NewsLettersDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "admin not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @DeleteMapping("/delete/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> deleteNewsLetters(@PathVariable Integer id) {
	        try {
	        	NewsLettersService.deleteNewsLetters(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @GetMapping("/")
		@PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<List<NewsLettersDTO>> findAll() {
	        try {
	            Iterable<NewsLetters> admins = NewsLettersService.getAllNewsLetters();
	            List<NewsLettersDTO> adminDTOs = new ArrayList<>();
	            for (NewsLetters admin : admins) {
	            	adminDTOs.add(admin.toNewsLettersDTO());
	            }
	            return ResponseEntity.ok(adminDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

		   

		
	    @PostMapping("/add")
		@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
	    public ResponseEntity<String> addNewsLetters(@RequestBody NewsLettersDTO adminDto) {
	    	
	    	NewsLetters admin =adminDto.toNewsLetters();
	    	 
	    	NewsLetters savedNewsLetters = NewsLettersService.saveNewsLetters(admin);
	    	
	        
	        // You can customize the confirmation message here
	        String confirmationMessage = "Comptable with ID  has been added successfully.";
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
	    }

		

	    @PutMapping("/update/{id}")

		@PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<NewsLettersDTO> updateNewsLetters(@PathVariable Integer id, @RequestBody NewsLettersDTO NewsLettersDto) {
	        try {
	            Optional<NewsLetters> NewsLettersOpt = NewsLettersService.getNewsLettersById(id);
	            if (NewsLettersOpt.isPresent()) {
	            	NewsLetters NewsLetters = NewsLettersOpt.get();
	                
	            	NewsLetters = NewsLettersDto.toNewsLetters();
	                
	            	NewsLetters = NewsLettersService.updateNewsLetters(id, NewsLetters);
	                
	            	NewsLettersDTO NewsLettersResponse = NewsLetters.toNewsLettersDTO();
	                
	                return ResponseEntity.ok(NewsLettersResponse);
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


}
