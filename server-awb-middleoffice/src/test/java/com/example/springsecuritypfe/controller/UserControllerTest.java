package com.example.springsecuritypfe.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
//for HTTP methods
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//for HTTP status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.example.springsecuritypfe.model.User;
import com.example.springsecuritypfe.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	User user;

	@Before
	public void setUp() {
		// Initialisation du setup avant chaque test
		user = new User("Dupont", "password");
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testFindUserByLogin() throws Exception {
		when(userService.findByLoginAndPassword("Dupont", "password")).thenReturn(Optional.ofNullable(user));
		User userTofindByLogin = new User("Dupont", "password");
		String jsonContent = objectMapper.writeValueAsString(userTofindByLogin);
		// on execute la requête
		MvcResult result = mockMvc
				.perform(post("/user/users/login").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
				.andExpect(status().isFound()).andReturn();

		assertEquals("Erreur de sauvegarde", HttpStatus.FOUND.value(), result.getResponse().getStatus());
		verify(userService).findByLoginAndPassword(any(String.class), any(String.class));
		User userResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<User>() {
		});
		assertNotNull(userResult);
		assertEquals(new Long(1), userResult.getId());
		assertEquals(userTofindByLogin.getUsername(), userResult.getUsername());
		assertEquals(userTofindByLogin.getPassword(), userResult.getPassword());
	}


}

