package com.example.springsecuritypfe.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecuritypfe.exception.BusinessResourceException;
import com.example.springsecuritypfe.model.AppUser;
import com.example.springsecuritypfe.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	

    private UserRepository userRepository;
    
    private PasswordEncoder passwordEncoder; 	//It will be provided on WebSecurityConfig as @Bean

    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
    
    @Override
	public Optional<AppUser> findUserById(Long id) throws  BusinessResourceException{
		
		Optional<AppUser> userFound = userRepository.findById(id);
		
        if (Boolean.FALSE.equals(userFound.isPresent())){
            throw new BusinessResourceException("User Not Found", "Aucun utilisateur avec l'identifiant :" + id);
        }
		return userFound;
	}
    
    
    @Override
	public AppUser saveOrUpdateUser(AppUser user) throws BusinessResourceException{
		try{
			if(null ==user.getId()) {  // pas d'Id --> création d'un user
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			} 
			else {  //sinon, mise à jour d'un user
				
				Optional<AppUser> userFromDB = findUserById(user.getId());
				
				user.setPassword(userFromDB.get().getPassword());
				
				/*if(! passwordEncoder.matches(user.getPassword(), userFromDB.get().getPassword())) {
					user.setPassword(passwordEncoder.encode(user.getPassword()));  // MAJ du mot de passe s'il a été modifié
				} 
				
				else {
					user.setPassword(userFromDB.get().getPassword()); //Sinon, on remet le password déjà haché
				}*/
			}
			
			AppUser result = userRepository.save(user);
			return  result;
			
		} catch(DataIntegrityViolationException ex){
			log.error("Utilisateur non existant", ex);
			throw new BusinessResourceException("DuplicateValueError", "Un utilisateur existe déjà avec le compte : "+user.getUsername(), HttpStatus.CONFLICT);
		
		} catch (BusinessResourceException e) {
			log.error("Utilisateur non existant", e);
			throw new BusinessResourceException("UserNotFound", "Aucun utilisateur avec l'identifiant: "+user.getId(), HttpStatus.NOT_FOUND);
		
		} catch(Exception ex){
			log.error("Erreur technique de création ou de mise à jour de l'utilisateur", ex);
			throw new BusinessResourceException("SaveOrUpdateUserError", "Erreur technique de création ou de mise à jour de l'utilisateur: "+user.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
    

	/*@Override
    public User saveUser(final User user) throws BusinessResourceException {
    try {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    } catch(DataIntegrityViolationException ex){
			logger.error("Utilisateur non existant", ex);
			throw new BusinessResourceException("DuplicateValueError", "Un utilisateur existe déjà avec le compte : "+user.getUsername(), HttpStatus.CONFLICT);
	} catch(Exception ex){
			logger.error("Erreur technique de création ou de mise à jour de l'utilisateur", ex);
			throw new BusinessResourceException("SaveUserError", "Erreur technique de création de l'utilisateur: "+user.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    //save = create or update
    @Override
    public User updateUser(final User user) throws BusinessResourceException {
        return userRepository.save(user);
    } catch(Exception ex){
			logger.error("Erreur technique de création ou de mise à jour de l'utilisateur", ex);
			throw new BusinessResourceException("UpdateUserError", "Erreur technique de mise à jour de l'utilisateur: "+user.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
    
    */

    @Override
    public void deleteUser(final Long userId){
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<AppUser> findByUsername(final String username) {
        return userRepository.findByUsername(username);
	}
    

    @Override
    public List<AppUser> findAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public Long numberOfUsers(){
        return userRepository.count();
    }

    @Override
	public Optional<AppUser> findByLoginAndPassword(String username, String password) throws BusinessResourceException{
		try {
			Optional<AppUser> userFound = this.findByUsername(username);
			if(passwordEncoder.matches(password, userFound.get().getPassword())) {
				return userFound;
			} else {
				throw new BusinessResourceException("UserNotFound", "Mot de passe incorrect", HttpStatus.NOT_FOUND);
			}
		} catch (BusinessResourceException ex) {
			log.error("Login ou mot de passe incorrect", ex);
			throw new BusinessResourceException("UserNotFound", "Login ou mot de passe incorrect", HttpStatus.NOT_FOUND);
		}catch (Exception ex) {
			log.error("Une erreur technique est survenue", ex);
			throw new BusinessResourceException("TechnicalError", "Une erreur technique est survenue", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}

