package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.NoteProduit;

@Repository
public interface NoteProduitRepository extends JpaRepository<NoteProduit, Integer> {

}
