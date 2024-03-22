package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.securingweb.Models.Formations;


public interface FormationsRepository extends JpaRepository<Formations, Integer> {

}
