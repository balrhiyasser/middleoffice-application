package com.example.springsecuritypfe.service;

import java.util.List;
import java.util.Optional;

import com.example.springsecuritypfe.exception.BusinessResourceException;
import com.example.springsecuritypfe.model.AppUser;

public interface UserService {
	
    /*User saveUser(User user);

    User updateUser(User user);*/

    void deleteUser(Long userId) throws BusinessResourceException ;

    Optional<AppUser> findByUsername(String username) throws BusinessResourceException ;

    List<AppUser> findAllUsers();

    Long numberOfUsers();

    AppUser saveOrUpdateUser(AppUser user) throws BusinessResourceException ;

	Optional<AppUser> findUserById(Long id) throws BusinessResourceException ;

	Optional<AppUser> findByLoginAndPassword(String username, String password) throws BusinessResourceException ;
}
