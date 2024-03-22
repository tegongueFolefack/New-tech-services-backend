package com.example.securingweb.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Enums.Role;
import com.example.securingweb.Enums.TokenType;
import com.example.securingweb.Models.Admin;
import com.example.securingweb.Models.RefreshToken;
import com.example.securingweb.Repository.AdminRepository;
import com.example.securingweb.Repository.RefreshTokenRepository;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;

@Service
public class AdminService {
	
	 @Autowired
	    private AdminRepository AdminRepository;
	 @Autowired
	 private  RefreshTokenRepository refreshTokenRepository;
	 @Autowired
	 private  PasswordEncoder passwordEncoder;
	 
	 @Autowired
	  private  JwtService jwtService;
	 
	
	 
	 @Autowired
	  private  AuthenticationManager authenticationManager;
	 
	 @Autowired
	  private  RefreshTokenService refreshTokenService;
	 

		public Optional<Admin> getAdminById(Long id) {
			return AdminRepository.findById(id);
		}

	
		public Iterable<Admin> getAllAdmin() {
			return AdminRepository.findAll();
		}

	
		public boolean deleteAdmin(Long id) {
	        // Recherche de l'administrateur par ID
	        Optional<Admin> adminOpt = getAdminById(id);
	        
	        if (adminOpt.isPresent()) {
	            Admin admin = adminOpt.get();
	            
	            // Récupération du refresh token associé à l'administrateur
	            Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByUserId(admin.getId());
	            
	            if (refreshTokenOpt.isPresent()) {
	                // Suppression du refresh token
	                refreshTokenRepository.delete(refreshTokenOpt.get());
	            }
	            
	            // Suppression de l'administrateur
	            AdminRepository.delete(admin);
	            
	            return true; // Opération de suppression réussie
	        } else {
	            // L'administrateur n'existe pas avec l'ID spécifié
	            return false; // Opération de suppression échouée
	        }
	    }
		
		
		
		public AuthenticationResponse updateAdmin(Long id, Admin admin) {
	        Optional<Admin> adminOpt = AdminRepository.findById(id);

	        if (adminOpt.isPresent()) {
	            Admin adminFromDb = adminOpt.get();

	            // Mettre à jour les propriétés de l'objet Admin avec les données de l'objet Admin passé en paramètre
	            adminFromDb.setNom(admin.getNom());
	            adminFromDb.setPrenom(admin.getPrenom());
	            adminFromDb.setEmail(admin.getEmail());
	            adminFromDb.setLogin(admin.getLogin());

	            // Vérifier si le mot de passe a été modifié
	            if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
	                // Hacher le nouveau mot de passe
	                String hashedPassword = passwordEncoder.encode(admin.getPassword());
	                adminFromDb.setPassword(hashedPassword);
	            }

	            // Sauvegarder les modifications dans la base de données
	            Admin updatedAdmin = AdminRepository.save(adminFromDb);

	            // Générer un nouveau token JWT avec les informations mises à jour
	            String jwt = jwtService.generateToken(updatedAdmin);

	            // Créer un nouveau refresh token pour l'utilisateur
	            RefreshToken refreshToken = refreshTokenService.createRefreshToken(updatedAdmin.getId());

	            // Récupérer les rôles de l'utilisateur mis à jour et les convertir en une liste de String
	            List<String> roles = updatedAdmin.getRole().getAuthorities().stream()
	                    .map(SimpleGrantedAuthority::getAuthority)
	                    .collect(Collectors.toList());

	            // Construire et renvoyer la réponse
	            return AuthenticationResponse.builder()
	                    .accessToken(jwt)
	                    .email(updatedAdmin.getEmail())
	                    .refreshToken(refreshToken.getToken())
	                    .roles(roles)
	                    .tokenType(TokenType.BEARER.name())
	                    .build();
	        } else {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	        }
	    }
	  
	
		
		
	  public AuthenticationResponse register(RegisterRequest request) {
		  Admin admin = new Admin(
		            request.getNom(),
		            request.getPrenom(),
		            request.getEmail(),
		            passwordEncoder.encode(request.getPassword()),
		            request.getLogin(),
		            Role.ADMIN 
		        );
	        admin = AdminRepository.save(admin);
	        var jwt = jwtService.generateToken(admin);
	        var refreshToken = refreshTokenService.createRefreshToken(admin.getId());

	        var roles = admin.getRole().getAuthorities()
	                .stream()
	                .map(SimpleGrantedAuthority::getAuthority)
	                .toList();

	        return AuthenticationResponse.builder()
	                .accessToken(jwt)
	                .email(admin.getEmail())
	                .id(admin.getId())
	                .refreshToken(refreshToken.getToken())
	                .roles(roles)
	                .tokenType( TokenType.BEARER.name())
	                .build();
	    }
	   
	  
	  
	  
	  public AuthenticationResponse authenticate(AuthenticationRequest request) {
		    authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		    // Utilisez .orElseThrow() pour gérer l'Optional
		    Admin admin = AdminRepository.findByEmail(request.getEmail())
		            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
		    
		    // Récupérez les rôles de l'administrateur
		    var roles = admin.getRole().getAuthorities()
		            .stream()
		            .map(SimpleGrantedAuthority::getAuthority)
		            .toList();

		    // Générez le jeton JWT et le jeton de rafraîchissement
		    var jwt = jwtService.generateToken(admin);
		    var refreshToken = refreshTokenService.createRefreshToken(admin.getId());

		    return AuthenticationResponse.builder()
		            .accessToken(jwt)
		            .roles(roles)
		            .email(admin.getEmail())
		            .id(admin.getId())
		            .refreshToken(refreshToken.getToken())
		            .tokenType(TokenType.BEARER.name())
		            .build();
		}


}
