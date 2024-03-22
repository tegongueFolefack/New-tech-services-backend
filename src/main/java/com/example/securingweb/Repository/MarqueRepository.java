package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Marque;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Integer> {

}
