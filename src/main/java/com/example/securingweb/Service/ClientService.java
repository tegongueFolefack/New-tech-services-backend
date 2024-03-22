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
import com.example.securingweb.Models.Client;
import com.example.securingweb.Models.RefreshToken;
import com.example.securingweb.Repository.ClientRepository;
import com.example.securingweb.Repository.RefreshTokenRepository;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;




@Service
public class ClientService {
	
		 @Autowired
		    private ClientRepository ClientRepository;
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
		 

			public Optional<Client> getClientById(Long id) {
				return ClientRepository.findById(id);
			}

		
			public Iterable<Client> getAllClient() {
				return ClientRepository.findAll();
			}

		
			 public boolean deleteClient(Long id) {
			        Optional<Client> ClientOpt = ClientRepository.findById(id);

			        if (ClientOpt.isPresent()) {
			            Client Client = ClientOpt.get();

			            // Récupération de tous les RefreshTokens associés au même userID
			            List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByUserId(Client.getId());

			            // Suppression de tous les RefreshTokens trouvés
			            refreshTokenRepository.deleteAll(refreshTokens);

			            // Suppression de l'Clientistrateur
			            ClientRepository.delete(Client);

			            return true; // Opération de suppression réussie
			        } else {
			            // L'Clientistrateur n'existe pas avec l'ID spécifié
			            return false; // Opération de suppression échouée
			        }
			    }
		    
			
			
			
			public AuthenticationResponse updateClient(Long id, Client Client) {
		        Optional<Client> ClientOpt = ClientRepository.findById(id);

		        if (ClientOpt.isPresent()) {
		            Client ClientFromDb = ClientOpt.get();

		            // Mettre à jour les propriétés de l'objet Client avec les données de l'objet Client passé en paramètre
		            ClientFromDb.setNom(Client.getNom());
		            ClientFromDb.setPrenom(Client.getPrenom());
		            ClientFromDb.setEmail(Client.getEmail());
		            ClientFromDb.setLogin(Client.getLogin());

		            // Vérifier si le mot de passe a été modifié
		            if (Client.getPassword() != null && !Client.getPassword().isEmpty()) {
		                // Hacher le nouveau mot de passe
		                String hashedPassword = passwordEncoder.encode(Client.getPassword());
		                ClientFromDb.setPassword(hashedPassword);
		            }

		            // Sauvegarder les modifications dans la base de données
		            Client updatedClient = ClientRepository.save(ClientFromDb);

		            // Générer un nouveau token JWT avec les informations mises à jour
		            String jwt = jwtService.generateToken(updatedClient);

		            // Créer un nouveau refresh token pour l'utilisateur
		            RefreshToken refreshToken = refreshTokenService.createRefreshToken(updatedClient.getId());

		            // Récupérer les rôles de l'utilisateur mis à jour et les convertir en une liste de String
		            List<String> roles = updatedClient.getRole().getAuthorities().stream()
		                    .map(SimpleGrantedAuthority::getAuthority)
		                    .collect(Collectors.toList());

		            // Construire et renvoyer la réponse
		            return AuthenticationResponse.builder()
		                    .accessToken(jwt)
		                    .email(updatedClient.getEmail())
		                    .refreshToken(refreshToken.getToken())
		                    .roles(roles)
		                    .tokenType(TokenType.BEARER.name())
		                    .build();
		        } else {
		            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		        }
		    }
		  
		
			
			
		  public AuthenticationResponse register(RegisterRequest request) {
			  Client Client = new Client(
			            request.getNom(),
			            request.getPrenom(),
			            request.getEmail(),
			            passwordEncoder.encode(request.getPassword()),
			            request.getLogin(),
			            Role.CLIENT
			        );
		        Client = ClientRepository.save(Client);
		        var jwt = jwtService.generateToken(Client);
		        var refreshToken = refreshTokenService.createRefreshToken(Client.getId());

		        var roles = Client.getRole().getAuthorities()
		                .stream()
		                .map(SimpleGrantedAuthority::getAuthority)
		                .toList();

		        return AuthenticationResponse.builder()
		                .accessToken(jwt)
		                .email(Client.getEmail())
		                .id(Client.getId())
		                .refreshToken(refreshToken.getToken())
		                .roles(roles)
		                .tokenType( TokenType.BEARER.name())
		                .build();
		    }
		   
		  
		  
		  
		  public AuthenticationResponse authenticate(AuthenticationRequest request) {
			    authenticationManager.authenticate(
			            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			    // Utilisez .orElseThrow() pour gérer l'Optional
			    Client Client = ClientRepository.findByEmail(request.getEmail())
			            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
			    
			    // Récupérez les rôles de l'Clientistrateur
			    var roles = Client.getRole().getAuthorities()
			            .stream()
			            .map(SimpleGrantedAuthority::getAuthority)
			            .toList();

			    // Générez le jeton JWT et le jeton de rafraîchissement
			    var jwt = jwtService.generateToken(Client);
			    var refreshToken = refreshTokenService.createRefreshToken(Client.getId());

			    return AuthenticationResponse.builder()
			            .accessToken(jwt)
			            .roles(roles)
			            .email(Client.getEmail())
			            .id(Client.getId())
			            .refreshToken(refreshToken.getToken())
			            .tokenType(TokenType.BEARER.name())
			            .build();
			}


}
