package com.example.securingweb.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.*;
import com.example.securingweb.Repository.*;

@Service
public class ImageService {
	
	@Autowired
    private ImageRepository ImageRepository;
	
	@Autowired
    private ServicesService ServicesService;
	
	public List<Image> getImagesByLibelle(String libelle) {
        return ImageRepository.findByLibelle(libelle);
    }

//	 public Optional<Image> getImageByLibelle(String libelle) {
//	        return ImageRepository.findByLibelle(libelle);
//	    }
	
	
	 
//    public String addService(ParamsImage ParamsImage) {
//        Image Image = ImageRepository.findById(ParamsImage.imageId).orElse(null);
//        if (Image != null) {
//            Services Services = ServicesService.getServicesById(ParamsImage.serviceId).orElse(null);
//            if (Services != null) {
//                Image.setService(Services);
//                ImageRepository.save(Image);
//                return "good";
//            }
//        }
//        return "error";
//    }
 

	public Optional<Image> getImageById(Integer id) {
		return ImageRepository.findById(id);
	}
	
	public String getImageAsBase64(Integer id) {
        Optional<Image> imageOpt = ImageRepository.findById(id);
        if (imageOpt.isPresent()) {
            Image image = imageOpt.get();
            byte[] imageData = image.getImageData();
            return Base64.getEncoder().encodeToString(imageData);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found with ID: " + id);
        }
    }

	public Iterable<Image> getAllImage() {
		return ImageRepository.findAll();
	}

	public boolean deleteImage(Integer id) {
		Optional<Image> ImageOpt = getImageById(id);
		if (ImageOpt.isPresent()) {
			ImageRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Image updateImage(Integer id, Image image2) {
	    Optional<Image> imageOpt = ImageRepository.findById(id);
	    
	    if (imageOpt.isPresent()) {
	        Image image = imageOpt.get();
	        
	        // Mise à jour des propriétés de l'objet Image avec les données de l'image2
	        image.setLibelle(image2.getLibelle());
	        image.setImageData(image2.getImageData()); // Aucune conversion nécessaire
	        
	        // Sauvegarder les modifications dans la base de données
	        return ImageRepository.save(image);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}



 
 public Image saveImage(Image Image) {
		return ImageRepository.save(Image);
	}

}