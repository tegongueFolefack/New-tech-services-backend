package com.example.securingweb.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Categorie;
import com.example.securingweb.Models.Marque;
import com.example.securingweb.Models.Produit;
import com.example.securingweb.Models.Services;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer>{
	List<Produit> findByCategorie_id(Integer categoryId);
	Produit findByImage_id(Integer categoryId);
	List<Produit> findByCategorie(Categorie categorie);
    List<Produit> findByMarque(Marque marque);

	List<Produit> findByPrixUnitaire(Double prix);
	List<Produit> findByMarque_id(Integer marqueId);

}
