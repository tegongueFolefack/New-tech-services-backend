package com.example.securingweb.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Categorie;



@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
	 Categorie findByIntitule(String intitule);
}
