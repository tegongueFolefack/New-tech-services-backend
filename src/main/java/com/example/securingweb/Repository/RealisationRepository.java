package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Realisations;

@Repository
public interface RealisationRepository extends JpaRepository<Realisations, Integer>{

}
