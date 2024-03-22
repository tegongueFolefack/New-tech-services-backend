package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Panier;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Integer>{

}
