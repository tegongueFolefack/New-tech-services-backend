package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.securingweb.Models.Blog;



public interface BlogRepository extends JpaRepository<Blog, Integer>{

}
