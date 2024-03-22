package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Livraison;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Integer> {

}
