package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.securingweb.Models.Image;


public interface ImageRepository extends JpaRepository<Image, Integer> {

}
