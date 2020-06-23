package com.example.springsecuritypfe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecuritypfe.model.AppUser;



public interface UserRepository extends JpaRepository<AppUser, Long> {
	
	//findBy + fieldName
    Optional<AppUser> findByUsername(String username);
}
