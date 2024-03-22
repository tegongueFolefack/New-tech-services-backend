package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer>{

}
