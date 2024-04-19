package com.example.securingweb.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.securingweb.Models.Produit;
import com.example.securingweb.Models.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer>{
	
//	@Query("SELECT s, p.image FROM Produit p JOIN Service s ON TYPE(s) = Produit AND s.id = p.id WHERE TYPE(s) = :type")
//	List<Object[]> findServicesAndImagesByType(@Param("type") String type);

	
}
