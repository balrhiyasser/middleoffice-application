package com.example.springsecuritypfe.service;

import java.util.List;
import java.util.Optional;

import com.example.springsecuritypfe.model.AppUser;

public interface UserService {
	
    /*User saveUser(User user);

    User updateUser(User user);*/

    void deleteUser(Long userId);

    Optional<AppUser> findByUsername(String username);

    List<AppUser> findAllUsers();

    Long numberOfUsers();

    AppUser saveOrUpdateUser(AppUser user) ;

	Optional<AppUser> findUserById(Long id) ;

	Optional<AppUser> findByLoginAndPassword(String username, String password);
}
