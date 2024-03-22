package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
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

import com.example.securingweb.DTO.BlogDTO;
import com.example.securingweb.Models.Blog;
import com.example.securingweb.Service.BlogService;
import com.example.securingweb.Service.BlogService;
import com.example.securingweb.Service.RefreshTokenService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@RestController
@RequestMapping("/Blog")
//@SecurityRequirements()
@RequiredArgsConstructor
public class BlogController {
	
	@Autowired
	private BlogService BlogService;

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Integer id) {
		 
        try {
        	
            Optional<Blog> Blog = BlogService.getBlogById(id);
            System.out.println("shgshgshdgadhs");
            if (Blog.isPresent()) {
            	
            	BlogDTO BlogDTO = Blog.get().toBlogDTO();
            
                return ResponseEntity.ok(BlogDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "admin not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("/delete/{id}")
   
    public ResponseEntity<Void> deleteBlog(@PathVariable Integer id) {
        try {
        	BlogService.deleteBlog(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<List<BlogDTO>> findAll() {
        try {
            Iterable<Blog> admins = BlogService.getAllBlog();
            List<BlogDTO> adminDTOs = new ArrayList<>();
            for (Blog admin : admins) {
            	adminDTOs.add(admin.toBlogDTO());
            }
            return ResponseEntity.ok(adminDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addBlog(@RequestBody BlogDTO adminDto) {
    	
    	Blog admin =adminDto.toBlog();
    	 
    	Blog savedBlog = BlogService.saveBlog(admin);
    	
        
        // You can customize the confirmation message here
        String confirmationMessage = "Comptable with ID  has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable Integer id, @RequestBody BlogDTO BlogDto) {
        try {
            Optional<Blog> BlogOpt = BlogService.getBlogById(id);
            if (BlogOpt.isPresent()) {
            	Blog Blog = BlogOpt.get();
                
            	Blog = BlogDto.toBlog();
                
            	Blog = BlogService.updateBlog(id, Blog);
                
            	BlogDTO BlogResponse = Blog.toBlogDTO();
                
                return ResponseEntity.ok(BlogResponse);
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
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }


}
