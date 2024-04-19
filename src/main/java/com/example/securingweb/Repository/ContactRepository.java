package com.example.securingweb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.Models.Commentaire;
import com.example.securingweb.Models.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
