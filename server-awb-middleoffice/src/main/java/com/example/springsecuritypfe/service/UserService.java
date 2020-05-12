package com.example.springsecuritypfe.service;

import java.util.List;
import java.util.Optional;

import com.example.springsecuritypfe.model.User;

public interface UserService {
	
    /*User saveUser(User user);

    User updateUser(User user);*/

    void deleteUser(Long userId);

    Optional<User> findByUsername(String username);

    List<User> findAllUsers();

    Long numberOfUsers();

	User saveOrUpdateUser(User user) ;

	Optional<User> findUserById(Long id) ;

	Optional<User> findByLoginAndPassword(String username, String password);
}
