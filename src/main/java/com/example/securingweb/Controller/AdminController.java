package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

import com.example.securingweb.DTO.*;
import com.example.securingweb.Models.*;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RefreshTokenRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;
import com.example.securingweb.Response.RefreshTokenResponse;
import com.example.securingweb.Service.AdminService;
import com.example.securingweb.Service.BlogService;
import com.example.securingweb.Service.CommandeService;
import com.example.securingweb.Service.FormationsService;
import com.example.securingweb.Service.InscritsService;
import com.example.securingweb.Service.UtilisateurService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.example.securingweb.Service.LikeService;
import com.example.securingweb.Service.RefreshTokenService;




@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@SecurityRequirements() 
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	
	@Autowired
	  private  RefreshTokenService refreshTokenService ;
	@Autowired
	  private  AuthenticationManager authenticationManager;

	@Autowired
	private AdminService adminService;
	

	
	@GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        try {
            Optional<Admin> admin = adminService.getAdminById(id);
            if (admin.isPresent()) {
            	AdminDTO adminDTO = admin.get().toAdminDTO();
                return ResponseEntity.ok(adminDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "admin not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        try {
        	
        	adminService.deleteAdmin(id);
        	
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<AdminDTO>> findAll() {
        try {
            Iterable<Admin> admins = adminService.getAllAdmin();
            List<AdminDTO> adminDTOs = new ArrayList<>();
            for (Admin admin : admins) {
            	adminDTOs.add(admin.toAdminDTO());
            }
            return ResponseEntity.ok(adminDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   
    @PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
	    return ResponseEntity.ok(adminService.register(request));
	 }
	 
	  @PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
	    return ResponseEntity.ok(adminService.authenticate(request));
	 }
	  @PostMapping("/refresh-token")
	  public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
	    return ResponseEntity.ok(refreshTokenService.generateNewToken(request));
	 }
	  @GetMapping("/info")
	    public Authentication getAuthentication(@RequestBody AuthenticationRequest request){
	        return authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
	    }
	


	
	  @PutMapping("/update/{id}")
	    public ResponseEntity<AuthenticationResponse> updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDto) {
	        try {
	            Admin admin = adminDto.toAdmin();
	            AuthenticationResponse response = adminService.updateAdmin(id, admin);
	            return ResponseEntity.ok(response);
	        } catch (ResponseStatusException e) {
	            throw e;
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
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
