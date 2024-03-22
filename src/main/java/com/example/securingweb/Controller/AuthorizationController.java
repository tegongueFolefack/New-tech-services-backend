package com.example.securingweb.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
 
 
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
@Tag(name = "Authorization", description = "The Authorization API. Contains a secure hello method")
public class AuthorizationController {
	
	@Autowired
	private AdminService adminService;
	
//	@GetMapping("/admin/resource")
//    @PreAuthorize("hasAuthority('READ_PRIVILEGE') and hasRole('ADMIN')")
//    @Operation(
//            description = "This endpoint require a valid JWT, ADMIN role with READ_PRIVILEGE",
//            summary = "Hello secured endpoint",
//            responses = {
//                    @ApiResponse(
//                            description = "Success",
//                            responseCode = "200"
//                    ),
//                    @ApiResponse(
//                            description = "Unauthorized / Invalid Token",
//                            responseCode = "401"
//                    )
//            }
//    )
//    public ResponseEntity<String> sayHelloWithRoleAdminAndReadAuthority() {
//        return ResponseEntity.ok("Hello, you have access to a protected resource that requires admin role and read authority.");
//    }
//
//    @DeleteMapping("/admin/resource")
//    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE') and hasRole('ADMIN')")
//    public ResponseEntity<String> sayHelloWithRoleAdminAndDeleteAuthority() {
//        return ResponseEntity.ok("Hello, you have access to a protected resource that requires admin role and delete authority.");
//    }
//    @PostMapping("/user/resource")
//    @PreAuthorize("hasAuthority('CREATE_PRIVILEGE') and hasRole('USER')")
//    public ResponseEntity<String> sayHelloWithRoleUserAndCreateAuthority() {
//        return ResponseEntity.ok("Hello, you have access to a protected resource that requires user role and create authority.");
//    }
//    @PutMapping("/user/resource")
//    @PreAuthorize("hasAuthority('UPDATE_PRIVILEGE') and hasRole('USER')")
//    public ResponseEntity<String> sayHelloWithRoleUserAndUpdateAuthority() {
//        return ResponseEntity.ok("Hello, you have access to a protected resource that requires user role and update authority.");
//    }
    
	@DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            description = "This endpoint require a valid JWT, ADMIN role with READ_PRIVILEGE",
            summary = "Hello secured endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        try {
        	adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("/admin/resource")
    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE') and hasRole('ADMIN')")
    public ResponseEntity<String> sayHelloWithRoleAdminAndDeleteAuthority() {
        return ResponseEntity.ok("Hello, you have access to a protected resource that requires admin role and delete authority.");
    }
    @PostMapping("/user/resource")
    @PreAuthorize("hasAuthority('CREATE_PRIVILEGE') and hasRole('USER')")
    public ResponseEntity<String> sayHelloWithRoleUserAndCreateAuthority() {
        return ResponseEntity.ok("Hello, you have access to a protected resource that requires user role and create authority.");
    }
    @PutMapping("/user/resource")
    @PreAuthorize("hasAuthority('UPDATE_PRIVILEGE') and hasRole('USER')")
    public ResponseEntity<String> sayHelloWithRoleUserAndUpdateAuthority() {
        return ResponseEntity.ok("Hello, you have access to a protected resource that requires user role and update authority.");
    }
}
