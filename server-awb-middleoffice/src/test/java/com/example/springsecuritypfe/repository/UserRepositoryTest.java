package com.example.springsecuritypfe.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springsecuritypfe.model.User;


@RunWith(SpringRunner.class) //permet d'établir une liaison entre JUnit et Spring
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;	
	@Autowired
    private UserRepository userRepository;
	
	User user = new User("Dupont", "password");
	
	@Before
	public void setup(){
	    entityManager.persist(user); // on sauvegarde l'objet user au début de chaque test
	    entityManager.flush();
	}
	@Test
	public void testFindAllUsers() {
	    List<User> users = userRepository.findAll();
	    assertThat(4, is(users.size())); // On a 3 Users dans le fichier d'initialisation data.sql et un utilisateur ajouté lors du setup du test
	}
	
	@Test
    public void testSaveUser(){
		User user = new User("Paul", "password");
		User userSaved =  userRepository.save(user);
		assertNotNull(userSaved.getId());
	    assertThat("Paul", is(userSaved.getUsername())); 
	    /*
	     * J'ai ajouté le framework hamcrest (fourni par Spring Boot) qui apporte plus de flexibilité 
	     * pour l'utilisation de la méthode de test assertThat().
	     */
	}
	
	@Test
	public void testFindByLogin() {
	    Optional<User> userFromDB = userRepository.findByUsername("yasser");	 
	    assertThat("yasser", is(userFromDB.get().getUsername())); // yasser a été crée lors de l'initialisation du fichier data.sql     
	}
	
	@Test
	public void testFindById() {
	    Optional<User> userFromDB = userRepository.findById(user.getId());	 
	    assertThat(user.getUsername(), is(userFromDB.get().getUsername())); // user a été crée lors du setup     
	}
	
	@Test
	public void testFindBy_Unknown_Id() {
	    Optional<User> userFromDB = userRepository.findById(50L);	 
	    assertEquals(Optional.empty(), Optional.ofNullable(userFromDB).get());
	}
	
	@Test
    public void testDeleteUser(){
		userRepository.deleteById(user.getId());
		Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());
		assertEquals(Optional.empty(), Optional.ofNullable(userFromDB).get());
	}
	
	/*@Test
	public void testUpdateUser() {//Test si le compte utilisateur est désactivé
	    Optional<User> userToUpdate = userRepository.findByUsername(user.getUsername());
	    userToUpdate.get().setActive(0);
	    userRepository.save(userToUpdate.get());	    
	    Optional<User> userUpdatedFromDB = userRepository.findByUsername(userToUpdate.get().getUsername());
	    assertNotNull(userUpdatedFromDB);
	    assertThat(0, is(userUpdatedFromDB.get().getActive()));
	}		*/
}

