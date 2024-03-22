package com.example.securingweb.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.Models.Admin;
import com.example.securingweb.Models.User;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	Optional<Admin> findByEmail(String email);


}
