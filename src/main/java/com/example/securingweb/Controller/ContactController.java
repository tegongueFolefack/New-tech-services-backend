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

import com.example.securingweb.DTO.ContactDTO;
import com.example.securingweb.Models.Contact;
import com.example.securingweb.Service.ContactService;

@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {
	

	@Autowired
	private ContactService ContactService;

	@GetMapping("/{id}")
	//@PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Integer id) {
		 
        try {
        	
            Optional<Contact> Contact = ContactService.getContactById(id);
            System.out.println("shgshgshdgadhs");
            if (Contact.isPresent()) {
            	
            	ContactDTO ContactDTO = Contact.get().toContactDTO();
            
                return ResponseEntity.ok(ContactDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "admin not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("/delete/{id}")
   
    public ResponseEntity<Void> deleteContact(@PathVariable Integer id) {
        try {
        	ContactService.deleteContact(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
   // @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<List<ContactDTO>> findAll() {
        try {
            Iterable<Contact> admins = ContactService.getAllContact();
            List<ContactDTO> adminDTOs = new ArrayList<>();
            for (Contact admin : admins) {
            	adminDTOs.add(admin.toContactDTO());
            }
            return ResponseEntity.ok(adminDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("/add")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addContact(@RequestBody ContactDTO adminDto) {
    	 System.out.println("shgshgshdgadhs");
    	Contact admin =adminDto.toContact();
    	 System.out.println("111111111111111");
    	Contact savedContact = ContactService.saveContact(admin);
    	
    	 System.out.println("22222222");
        // You can customize the confirmation message here
        String confirmationMessage = "Comptable with ID  has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	

    @PutMapping("/update/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Integer id, @RequestBody ContactDTO ContactDto) {
        try {
            Optional<Contact> ContactOpt = ContactService.getContactById(id);
            if (ContactOpt.isPresent()) {
            	Contact Contact = ContactOpt.get();
                
            	Contact = ContactDto.toContact();
                
            	Contact = ContactService.updateContact(id, Contact);
                
            	ContactDTO ContactResponse = Contact.toContactDTO();
                
                return ResponseEntity.ok(ContactResponse);
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
