package com.example.securingweb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
	
	@Autowired
	private  JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	  private  AuthenticationProvider authenticationProvider;
	@Autowired
	  private  Http401UnauthorizedEntryPoint unauthorizedEntryPoint;
	@Autowired
	  private  CustomAccessDeniedHandler accessDeniedHandler;
	  
	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.csrf(AbstractHttpConfigurer::disable)
	                .exceptionHandling(exception -> exception
	                        .authenticationEntryPoint(unauthorizedEntryPoint)
	                        .accessDeniedHandler(accessDeniedHandler))
	                .authorizeHttpRequests(request ->
	                        request
	                                .requestMatchers(
	                                		"/Commande/**",
	                                		"/Marque/**",
	                                		"/Commentaire/**",
	                                		"/admin/register",
	                                		"/Categorie/**",
	                                		"/admin/**",
	                                		"/api/v1/auth/**",
	                                		"/client/**",
	                                		"/Panier/**",
	                                		"/Services/**",
	                                		"/Produit/**",
	                                		"/Like/**",
	                                		"/Image/**",
	                                		"/Services/**",
	                                		"/NoteProduit/**",
	                                        "/v2/api-docs",
	                                        "/v3/api-docs",
	                                        "/v3/api-docs/**",
	                                        "/swagger-resources",
	                                        "/swagger-resources/**",
	                                        "/configuration/ui",
	                                        "/configuration/security",
	                                        "/swagger-ui/**",
	                                        "/webjars/**",
	                                        "/swagger-ui.html"

	                                ).permitAll()
	                                .requestMatchers("/admin/update/{id}").hasAnyRole("ADMIN")
	                                .requestMatchers("/admin/delete/{id}").hasAnyRole("ADMIN")
	                               .requestMatchers("/Realisation/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/Realisation/{id}").hasAnyRole("CLIENT")
	                                .requestMatchers("/Realisation/").hasAnyRole("CLIENT")
	                                .requestMatchers("/admin/info").hasAnyRole("ADMIN")
	                                .requestMatchers("/client/update/{id}").hasAnyRole("CLIENT")
	                                .requestMatchers("/client/delete/{id}").hasAnyRole("CLIENT")
	                                //.requestMatchers("/client/register").hasAnyRole("CLIENT")
	                                //.requestMatchers("/client/refresh-token").hasAnyRole("CLIENT")
	                                .requestMatchers("/client/info").hasAnyRole("CLIENT")
	                                .requestMatchers("/client/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/Blog/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/utilisateur/**").hasAnyRole("ADMIN")
	                                //.requestMatchers("/Categorie/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/Blog/{id}").hasAnyRole("CLIENT")
	                                .requestMatchers("/Blog/").hasAnyRole("CLIENT")
	                                .requestMatchers("/Commande/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/Commande/**").hasAnyRole("CLIENT")
	                                .requestMatchers("/Commentaire/**").hasAnyRole("CLIENT")
	                                .requestMatchers("/Commentaire/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/formations/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/formations/{id}").hasAnyRole("CLIENT")
	                                .requestMatchers("/formations/").hasAnyRole("CLIENT")
	                                //.requestMatchers("/Image/**").hasAnyRole("ADMIN")
	                                //.requestMatchers("/Image/{id}").hasAnyRole("CLIENT")
	                                //.requestMatchers("/Produit/").hasAnyRole("CLIENT")
	                               // .requestMatchers("/Produit/{id}").hasAnyRole("CLIENT")
	                                //.requestMatchers("/Produit/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/Realisations/").hasAnyRole("CLIENT")
	                                .requestMatchers("/Realisations/{id}").hasAnyRole("CLIENT")
	                                .requestMatchers("/Realisations/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/Reduction/").hasAnyRole("CLIENT")
	                                .requestMatchers("/Reduction/{id}").hasAnyRole("CLIENT")
	                                .requestMatchers("/Reduction/**").hasAnyRole("ADMIN")
	                                //.requestMatchers("/Services/").hasAnyRole("CLIENT")
	                               // .requestMatchers("/Services/{id}").hasAnyRole("CLIENT")
	                                //.requestMatchers("/Services/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/Image/").hasAnyRole("CLIENT")
	                                .requestMatchers("/Inscrits/add").hasAnyRole("CLIENT")
	                                .requestMatchers("/Inscrits/**").hasAnyRole("ADMIN")
	                                //.requestMatchers("/Like/**").hasAnyRole("CLIENT")
	                                //.requestMatchers("/Like/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/NewsLetters/**").hasAnyRole("ADMIN")
	                                .requestMatchers("/NewsLetters/add").hasAnyRole("CLIENT") 
	                                .requestMatchers(HttpMethod.DELETE,"/api/v1/**").hasRole("ADMIN")
	                                .anyRequest().authenticated())
	                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
	                .authenticationProvider(authenticationProvider).addFilterBefore(
	                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        return http.build();
	    }
}
  