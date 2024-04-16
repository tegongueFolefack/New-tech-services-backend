package com.example.securingweb.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.Models.Image;


public interface ImageRepository extends JpaRepository<Image, Integer> {
//	List<Image> findByService_id(Integer serviceId);
List<Image> findByProduits_id(Integer produitId);
//	//Optional<Image> findByLibelle(String libelle);
	 List<Image> findByLibelle(String libelle);

}
