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

import com.example.securingweb.DTO.ServicesDTO;
import com.example.securingweb.Models.Services;
import com.example.securingweb.Service.ServicesService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations Services login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/Services")
//@PreAuthorize("hasRole('ADMIN')")
public class ServiceControlleur {
	
	
			@Autowired
			private ServicesService ServicesService;
			
			
			
			@GetMapping("/{id}")
			//@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
		    public ResponseEntity<ServicesDTO> getServicesById(@PathVariable Integer id) {
		        try {
		            Optional<Services> Services = ServicesService.getServicesById(id);
		            if (Services.isPresent()) {
		            	ServicesDTO ServicesDTO = Services.get().toServicesDTO();
		                return ResponseEntity.ok(ServicesDTO);
		            } else {
		                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Services not found with ID: " + id);
		            }
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

		    @DeleteMapping("delete/{id}")
		    @PreAuthorize("hasRole('ADMIN')")
		    public ResponseEntity<Void> deleteServices(@PathVariable Integer id) {
		        try {
		        	ServicesService.deleteServices(id);
		            return ResponseEntity.noContent().build();
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

		    @GetMapping("/")
		   // @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
		    public ResponseEntity<List<ServicesDTO>> findAll() {
		        try {
		            Iterable<Services> Servicess = ServicesService.getAllServices();
		            List<ServicesDTO> ServicesDTOs = new ArrayList<>();
		            for (Services Services: Servicess) {
		            	ServicesDTOs.add(Services.toServicesDTO());
		            }
		            return ResponseEntity.ok(ServicesDTOs);
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

			   

			
		    @PostMapping("/add")
		  //  @PreAuthorize("hasRole('ADMIN')")
		    public ResponseEntity<String> addServices(@RequestBody ServicesDTO ServicesDto) {
		    	Services Services = ServicesDto.toServices();
		    	Services savedServices = ServicesService.saveServices(Services);
		        
		        // You can customize the confirmation message here
		        String confirmationMessage = "Services with ID " + savedServices.getId() + " has been added successfully.";
		        
		        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
		    }

			
			    @PutMapping("update/{id}")
			  //  @PreAuthorize("hasRole('ADMIN')")
			    public ResponseEntity<ServicesDTO> updateFonction(@PathVariable Integer id, @RequestBody ServicesDTO ServicesDto) {
			        try {
			            Optional<Services> ServicesOpt = ServicesService.getServicesById(id);
			            if (ServicesOpt.isPresent()) {
			            	Services Services =ServicesOpt.get();
			                
			            	Services = ServicesDto.toServices();
			                
			            	Services = ServicesService.updateServices(id, Services);
			                
			            	ServicesDTO ServicesResponse = Services.toServicesDTO();
			                
			                return ResponseEntity.ok(ServicesResponse);
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
