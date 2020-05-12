package com.example.springsecuritypfe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecuritypfe.model.User;



public interface UserRepository extends JpaRepository<User, Long> {
	
	//findBy + fieldName
    Optional<User> findByUsername(String username);
}
