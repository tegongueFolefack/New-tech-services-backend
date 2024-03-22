package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.securingweb.Models.Like;


public interface LikeRepository extends JpaRepository<Like, Integer>{

}
