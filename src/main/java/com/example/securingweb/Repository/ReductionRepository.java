package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Reduction;

@Repository
public interface ReductionRepository extends JpaRepository<Reduction, Integer> {

}
