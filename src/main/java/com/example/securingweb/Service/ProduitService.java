package com.example.securingweb.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Categorie;
import com.example.securingweb.Models.Image;
import com.example.securingweb.Models.Marque;
import com.example.securingweb.Models.Params;
import com.example.securingweb.Models.Produit;
import com.example.securingweb.Models.Services;
import com.example.securingweb.Repository.ImageRepository;
import com.example.securingweb.Repository.ServicesRepository;
import com.example.securingweb.Repository.ProduitRepository;

@Service
public class ProduitService {
	
	@Autowired
    private ProduitRepository ProduitRepository;
	
	
	@Autowired
    private CategorieService categorieService;

    @Autowired
    private MarqueService marqueService;
    
    @Autowired
   private ImageRepository imageRepository;
    @Autowired
    private ServicesRepository ServicesRepository;
    
//    public Categorie getCategorieDuProduit(Produit produit) {
//        return produit.getCategorie();
//    }
//
//    public Marque getMarqueDuProduit(Produit produit) {
//        return produit.getMarque();
//    }
//    public List<Produit> getProduitsPrixInférieur(Double prix) {
//        return ProduitRepository.findByPrixInférieur(prix);
//    }
   
    public List<Image> getImagesById() {
        try {
            // Récupérer les produits l
            Iterable<Produit> produits = getAllProduit();

            // Initialiser une liste pour stocker les images
            List<Image> images = new ArrayList<>();

            // Parcourir chaque produit pour récupérer les images associées
            for (Produit produit : produits) {
                // Récupérer l'objet Image lié à ce produit
                Image image = produit.getImage();
                

                // Vérifier si l'objet Image est valide
                if (image != null) {
                    images.add(image);
                }
            }

            return images;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la récupération des images par ID de catégorie : " + e.getMessage());
        }
    }
    public List<Image> getImagesByMarqueId(Integer marqueId) {
        try {
            // Récupérer les produits liés à la catégorie
            List<Produit> produits = ProduitRepository.findByMarque_id(marqueId);

            // Initialiser une liste pour stocker les images
            List<Image> images = new ArrayList<>();

            // Parcourir chaque produit pour récupérer les images associées
            for (Produit produit : produits) {
                // Récupérer l'objet Image lié à ce produit
                Image image = produit.getImage();

                // Vérifier si l'objet Image est valide
                if (image != null) {
                    images.add(image);
                }
            }

            return images;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la récupération des images par ID de catégorie : " + e.getMessage());
        }
    }
    
    public List<Image> getImagesByCategoryId(Integer categoryId) {
        try {
            // Récupérer les produits liés à la catégorie
            List<Produit> produits = ProduitRepository.findByCategorie_id(categoryId);

            // Initialiser une liste pour stocker les images
            List<Image> images = new ArrayList<>();

            // Parcourir chaque produit pour récupérer les images associées
            for (Produit produit : produits) {
                // Récupérer l'objet Image lié à ce produit
                Image image = produit.getImage();

                // Vérifier si l'objet Image est valide
                if (image != null) {
                    images.add(image);
                }
            }

            return images;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la récupération des images par ID de catégorie : " + e.getMessage());
        }
    }


    public String addCategoryAndBrandToProduct(Params params) {
        Produit produit = ProduitRepository.findById(params.productId).orElse(null);
        if (produit != null) {
            Categorie categorie = categorieService.getCategorieById(params.categoryId).orElse(null);
            Marque marque = marqueService.getMarqueById(params.brandId).orElse(null);
            if (categorie != null && marque != null) {
                produit.setCategorie(categorie);
                produit.setMarque(marque);
                ProduitRepository.save(produit);
                return "good";
            }
        }
        return "error";
    }
 

	public Optional<Produit> getProduitById(Integer id) {
		return ProduitRepository.findById(id);
	}

	public Iterable<Produit> getAllProduit() {
		return ProduitRepository.findAll();
	}

	public boolean deleteProduit(Integer id) {
		Optional<Produit> ProduitOpt = getProduitById(id);
		if (ProduitOpt.isPresent()) {
			ProduitRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Produit updateProduit(Integer id, Produit Produit2) {
	    Optional<Produit> ProduitOpt = ProduitRepository.findById(id);
	    
	    if (ProduitOpt.isPresent()) {
	    	Produit Produit = ProduitOpt.get();
	    	
	        
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Produit.setQuantiteDisponible(Produit2.getQuantiteDisponible());
	    	Produit.setPrixUnitaire(Produit2.getPrixUnitaire());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return ProduitRepository.save(Produit);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Produit saveProduit(Produit Produit) {
		return ProduitRepository.save(Produit);
	}
 
 

}
