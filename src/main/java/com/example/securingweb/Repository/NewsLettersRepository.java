package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.NewsLetters;

@Repository
public interface NewsLettersRepository extends JpaRepository<NewsLetters, Integer>{

}
