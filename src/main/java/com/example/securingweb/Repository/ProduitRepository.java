package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer>{

}
