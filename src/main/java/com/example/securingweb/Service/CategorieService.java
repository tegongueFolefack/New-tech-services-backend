package com.example.securingweb.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Categorie;
import com.example.securingweb.Models.Image;
import com.example.securingweb.Models.ParamsCategorie;
import com.example.securingweb.Models.Produit;
import com.example.securingweb.Repository.CategorieRepository;
import com.example.securingweb.Repository.ImageRepository;

@Service
public class CategorieService {
	
	 @Autowired
	    private CategorieRepository CategorieRepository;
	 
	 @Autowired
	 private  ImageRepository imageRepository;
	 
	 @Autowired
	 private ProduitService ProduitService;
	 
	 
	 public List<Image> getImagesByCategoryName(String categoryName) {
	        // Récupérer la catégorie par son nom
	        Categorie categorie = CategorieRepository.findByIntitule(categoryName);
	        if (categorie == null) {
	            // Si la catégorie n'existe pas, retourner une liste vide
	            return List.of();
	        }
	        // Récupérer les images associées à la catégorie
	        return imageRepository.findByLibelle(categoryName);
	    }
	 

		public Optional<Categorie> getCategorieById(Integer id) {
			return CategorieRepository.findById(id);
		}

		public Iterable<Categorie> getAllCategorie() {
			return CategorieRepository.findAll();
		}

		public boolean deleteCategorie(Integer id) {
			Optional<Categorie> CategorieOpt = getCategorieById(id);
			if (CategorieOpt.isPresent()) {
				CategorieRepository.deleteById(id);
				return true;
			} else {
				return false;
			}
		}
		
		public Categorie updateCategorie(Integer id, Categorie Categorie2) {
		    Optional<Categorie> CategorieOpt = CategorieRepository.findById(id);
		    
		    if (CategorieOpt.isPresent()) {
		    	Categorie Categorie = CategorieOpt.get();
		        
		        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
		    	Categorie.setIntitule(Categorie2.getIntitule());
		    	
		        
		        // Sauvegarder les modifications dans la base de données
		        return CategorieRepository.save(Categorie);
		    } else {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		    }
		}

	 
	 public Categorie saveCategorie(Categorie Categorie) {
			return CategorieRepository.save(Categorie);
		}
	 
	 public String addProduit(ParamsCategorie para) {
		  Categorie cat = CategorieRepository.findById(para.categorieId).get();
		  Produit pro = ProduitService.getProduitById(para.produitId).get();
		  if(pro != null && cat != null) {
			  cat.getProduits().add(pro);
			 // this.saveSeminar(semi);
			  CategorieRepository.save(cat);
		  
		return "good";
		}
		return "error";
		  
		 
	  }

}
