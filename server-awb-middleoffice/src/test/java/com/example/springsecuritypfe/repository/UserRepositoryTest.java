package com.example.springsecuritypfe.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springsecuritypfe.model.AppUser;
import com.example.springsecuritypfe.model.Role;


@RunWith(SpringRunner.class) //permet d'établir une liaison entre JUnit et Spring
@DataJpaTest 
/*@DataJpaTest est une implémentation Spring de JPA qui fournit une configuration intégrée de la base de données MySQL, Hibernate, SpringData,
 et la DataSource. Cette annotation active également la détection des entités annotées par Entity, et intègre aussi la gestion des logs SQL.*/

@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;	
	@Autowired
    private UserRepository userRepository;
	
	AppUser user = new AppUser("Dupont","Nicolas Dupont",Role.USER, "password");
	
	@Before
	public void setup(){
	    entityManager.persist(user); // on sauvegarde l'objet user au début de chaque test
	    entityManager.flush();
	}
			
	@Test
	public void testFindAllUsers() {
	    List<AppUser> users = userRepository.findAll();
	    assertThat(1, is(users.size())); // On a 3 Users dans le fichier d'initialisation data.sql et un utilisateur ajouté lors du setup du test
	}
	
	@Test
    public void testSaveUser(){
		AppUser user = new AppUser("Paul","John Paul",Role.USER, "password");
		AppUser userSaved =  userRepository.save(user);
		assertNotNull(userSaved.getId());
	    assertThat("Paul", is(userSaved.getUsername())); 
	    /*
	     * J'ai ajouté le framework hamcrest (fourni par Spring Boot) qui apporte plus de flexibilité 
	     * pour l'utilisation de la méthode de test assertThat().
	     */
	}
	
	@Test
	public void testFindByLogin() {
	    Optional<AppUser> userFromDB = userRepository.findByUsername("Dupont");	 
	    assertThat("Dupont", is(userFromDB.get().getUsername())); // yasser a été crée lors de l'initialisation du fichier data.sql     
	}
	
	@Test
	public void testFindById() {
	    Optional<AppUser> userFromDB = userRepository.findById(user.getId());	 
	    assertThat(user.getUsername(), is(userFromDB.get().getUsername())); // user a été crée lors du setup     
	}
	
	@Test
	public void testFindBy_Unknown_Id() {
	    Optional<AppUser> userFromDB = userRepository.findById(50L);	 
	    assertEquals(Optional.empty(), Optional.ofNullable(userFromDB).get());
	}
	
	@Test
    public void testDeleteUser(){
		userRepository.deleteById(user.getId());
		Optional<AppUser> userFromDB = userRepository.findByUsername(user.getUsername());
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

