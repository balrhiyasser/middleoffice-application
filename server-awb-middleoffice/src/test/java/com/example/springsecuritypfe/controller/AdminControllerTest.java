package com.example.springsecuritypfe.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
//for HTTP methods
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//for HTTP status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.springsecuritypfe.model.AppUser;
import com.example.springsecuritypfe.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AdminController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class AdminControllerTest {
	
	/*
	 * Ce contrôleur ( AdminController ) dépend de la couche de service. Il ne connait pas la couche DAO. Il faut donc créer un Mock Object de
	 * 
	 * la couche de service grâce à l'annotation déjà vue précédemment @MockBean. Et pour indiquer le contrôleur à tester, 
	 *  
	 * il faut utiliser l'annotation @WebMvcTest 
	 * 
	 * en lui passant en paramètre la classe à tester. Cette annotation va aussi apporter toutes les dépendances SpringMVC nécessaires pour le cas de test.
	 * 
	 * Grâce à l'annotation @WebMvcTest, on peut injecter un MockMvc qui servira à construire des URL pour générer des requêtes HTTP.
	 * 
	 */

	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;
	
	AppUser user;

	@Before
	public void setUp() {
		// Initialisation du setup avant chaque test
		user = new AppUser("Dupont", "password");
		List<AppUser> allUsers = Arrays.asList(user);
		objectMapper = new ObjectMapper();

		// Mock de la couche de service
		when(userService.findAllUsers()).thenReturn(allUsers);
		when(userService.findUserById(any(Long.class))).thenReturn(Optional.of(user));

	}

	@Test
	public void testFindAllUsers() throws Exception {

		MvcResult result = mockMvc.perform(get("/user/users").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound()).andReturn();

		// ceci est une redondance, car déjà vérifié par: isFound())
		assertEquals("Réponse incorrecte", HttpStatus.FOUND.value(), result.getResponse().getStatus());
		verify(userService).findAllUsers();
		assertNotNull(result);
		Collection<AppUser> users = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<Collection<AppUser>>() {
				});
		assertNotNull(users);
		assertEquals(1, users.size());
		AppUser userResult = users.iterator().next();
		assertEquals(user.getUsername(), userResult.getUsername());
		assertEquals(user.getPassword(), userResult.getPassword());

	}

	@Test
	public void testSaveUser() throws Exception {

		AppUser userToSave = new AppUser("Dupont", "password");
		String jsonContent = objectMapper.writeValueAsString(userToSave);
		when(userService.saveOrUpdateUser(any(AppUser.class))).thenReturn(user);
		MvcResult result = mockMvc
				.perform(post("/user/users").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
				.andExpect(status().isCreated()).andReturn();

		assertEquals("Erreur de sauvegarde", HttpStatus.CREATED.value(), result.getResponse().getStatus());
		verify(userService).saveOrUpdateUser(any(AppUser.class));
		AppUser userResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<AppUser>() {
		});
		assertNotNull(userResult);
		assertEquals(userToSave.getUsername(), userResult.getUsername());
		assertEquals(userToSave.getPassword(), userResult.getPassword());

	}

	@Test
	public void testFindUserByLogin() throws Exception {
		when(userService.findByLoginAndPassword("Dupont", "password")).thenReturn(Optional.ofNullable(user));
		AppUser userTofindByLogin = new AppUser("Dupont", "password");
		String jsonContent = objectMapper.writeValueAsString(userTofindByLogin);
		// on execute la requête
		MvcResult result = mockMvc
				.perform(post("/user/users/login").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
				.andExpect(status().isFound()).andReturn();

		assertEquals("Erreur de sauvegarde", HttpStatus.FOUND.value(), result.getResponse().getStatus());
		verify(userService).findByLoginAndPassword(any(String.class), any(String.class));
		AppUser userResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<AppUser>() {
		});
		assertNotNull(userResult);
		assertEquals(new Long(1), userResult.getId());
		assertEquals(userTofindByLogin.getUsername(), userResult.getUsername());
		assertEquals(userTofindByLogin.getPassword(), userResult.getPassword());
	}

	@Test
	public void testDeleteUser() throws Exception {

		MvcResult result = mockMvc.perform(delete("/user/users/").param("id", "1"))
				.andExpect(status().isGone()).andReturn();
		assertEquals("Erreur de suppression", HttpStatus.GONE.value(), result.getResponse().getStatus());
		verify(userService).deleteUser(any(Long.class));
	}

	@Test
	public void testUpdateUser() throws Exception {

		AppUser userToUpdate = new AppUser("Toto", "password");
		AppUser userUpdated = new AppUser(2L, "Toto", "password");
		String jsonContent = objectMapper.writeValueAsString(userToUpdate);
		when(userService.saveOrUpdateUser(userToUpdate)).thenReturn(userUpdated);
		// on execute la requête
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put("/user/users/").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(jsonContent))
				.andExpect(status().isOk()).andReturn();

		verify(userService).saveOrUpdateUser(any(AppUser.class));
		AppUser userResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<AppUser>() {
		});
		assertNotNull(userResult);
		assertEquals(new Long(2), userResult.getId());
		assertEquals(userToUpdate.getUsername(), userResult.getUsername());
		assertEquals(userToUpdate.getPassword(), userResult.getPassword());
	}

}
