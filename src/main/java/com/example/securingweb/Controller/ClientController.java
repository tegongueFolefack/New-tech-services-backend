package com.example.securingweb.Controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.example.securingweb.DTO.ClientDTO;
import com.example.securingweb.DTO.ClientDTO;
import com.example.securingweb.Models.Client;
import com.example.securingweb.Models.Client;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RefreshTokenRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;
import com.example.securingweb.Response.RefreshTokenResponse;
import com.example.securingweb.Service.ClientService;
import com.example.securingweb.Service.RefreshTokenService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@SecurityRequirements() 
@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	  private  RefreshTokenService refreshTokenService ;
	@Autowired
	  private  AuthenticationManager authenticationManager;
	
	@Autowired
	private ClientService ClientService;

		@GetMapping("/{id}")
	    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
	        try {
	            Optional<Client> Client = ClientService.getClientById(id);
	            if (Client.isPresent()) {
	            	ClientDTO ClientDTO = Client.get().toClientDTO();
	                return ResponseEntity.ok(ClientDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comptable not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
		
		 

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<Void> deleteComptable(@PathVariable Long id) {
	        try {
	        	System.out.println("Hello");
	        	ClientService.deleteClient(id);
	        	System.out.println("Hello333");
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    
	    
	    @GetMapping("/")
	    public ResponseEntity<List<ClientDTO>> findAll() {
	        try {
	            Iterable<Client> Clients = ClientService.getAllClient();
	            List<ClientDTO> ClientDTOs = new ArrayList<>();
	            for (Client Client : Clients) {
	            	ClientDTOs.add(Client.toClientDTO());
	            }
	            return ResponseEntity.ok(ClientDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

		   

		
	    @PostMapping("/register")
	    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
	    	System.out.println("ggggggg");
	        try {
	            AuthenticationResponse response = ClientService.register(request);
	            return ResponseEntity.ok(response);
	        } catch (ResponseStatusException e) {
	            throw e;
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while registering the client", e);
	        }
	    }

		 
		  @PostMapping("/authenticate")
		  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		    return ResponseEntity.ok(ClientService.authenticate(request));
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
		    public ResponseEntity<AuthenticationResponse> updateClient(@PathVariable Long id, @RequestBody ClientDTO ClientDto) {
		        try {
		            Client client = ClientDto.toClient();
		            AuthenticationResponse response = ClientService.updateClient(id, client);
		            return ResponseEntity.ok(response);
		        } catch (ResponseStatusException e) {
		            throw e;
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
		        }
		    }
		    
		    
		   
		    	
//		    @ExceptionHandler(ResponseStatusException.class)
//		    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
//		        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
//		    }
//
//		    @ExceptionHandler(Exception.class)
//		    public ResponseEntity<String> handleException(Exception ex) {
//		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//		    }

	}



