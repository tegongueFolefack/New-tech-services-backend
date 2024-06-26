package com.example.securingweb.config;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.securingweb.Service.JwtService;

import java.io.IOException;
 
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	 
	@Autowired
	  private JwtService jwtService;
	@Autowired
	  private  UserDetailsService userDetailsService;
	 
	  @Override
	    protected void doFilterInternal(
	           @NonNull HttpServletRequest request,
	           @NonNull HttpServletResponse response,
	           @NonNull FilterChain filterChain
	    ) throws ServletException, IOException {
	        final String authHeader = request.getHeader("Authorization");
	        if(authHeader ==  null || !authHeader.startsWith("Bearer ")){
	            filterChain.doFilter(request, response);
	            return;
	        }
	        final String jwt = authHeader.substring(7); // after "Bearer "
	        final String userEmail =jwtService.extractUserName(jwt);
	        /*
	           SecurityContextHolder: is where Spring Security stores the details of who is authenticated.
	           Spring Security uses that information for authorization.*/

	        if(StringUtils.isNotEmpty(userEmail)
	                && SecurityContextHolder.getContext().getAuthentication() == null){
	            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
	            if(jwtService.isTokenValid(jwt, userDetails)){
	                //update the spring security context by adding a new UsernamePasswordAuthenticationToken
	                SecurityContext context = SecurityContextHolder.createEmptyContext();
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                        userDetails,
	                        null,
	                        userDetails.getAuthorities());
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                context.setAuthentication(authToken);
	                SecurityContextHolder.setContext(context);
	            }
	        }
	        filterChain.doFilter(request,response);
	    }

}
