package com.example.securingweb.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer>{
	
}
