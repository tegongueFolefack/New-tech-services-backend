package com.example.securingweb.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findByEmail(String email);
	
}
