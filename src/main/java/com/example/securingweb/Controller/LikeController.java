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

import com.example.securingweb.DTO.LikeDTO;
import com.example.securingweb.Models.Like;
import com.example.securingweb.Service.LikeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.securingweb.Service.LikeService;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/Like")

public class LikeController {
	
	@Autowired
	private LikeService LikeService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<LikeDTO> getLikeById(@PathVariable Integer id) {
        try {
            Optional<Like> Like = LikeService.getLikeById(id);
            if (Like.isPresent()) {
            	LikeDTO LikeDTO = Like.get().toLikeDTO();
                return ResponseEntity.ok(LikeDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Like not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<Void> deleteLike(@PathVariable Integer id) {
        try {
        	LikeService.deleteLike(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<List<LikeDTO>> findAll() {
        try {
            Iterable<Like> Likes = LikeService.getAllLike();
            List<LikeDTO> LikeDTOs = new ArrayList<>();
            for (Like Like: Likes) {
            	LikeDTOs.add(Like.toLikeDTO());
            }
            return ResponseEntity.ok(LikeDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<String> addLike(@RequestBody LikeDTO LikeDto) {
    	Like Like = LikeDto.toLike();
    	Like savedLike = LikeService.saveLike(Like);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Like with ID " + savedLike.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
	    public ResponseEntity<LikeDTO> updateFonction(@PathVariable Integer id, @RequestBody LikeDTO LikeDto) {
	        try {
	            Optional<Like> LikeOpt = LikeService.getLikeById(id);
	            if (LikeOpt.isPresent()) {
	            	Like Like =LikeOpt.get();
	                
	            	Like = LikeDto.toLike();
	                
	            	Like = LikeService.updateLike(id, Like);
	                
	            	LikeDTO LikeResponse = Like.toLikeDTO();
	                
	                return ResponseEntity.ok(LikeResponse);
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
