package com.example.springsecuritypfe.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.springsecuritypfe.exception.BusinessResourceException;
import com.example.springsecuritypfe.model.User;
import com.example.springsecuritypfe.repository.UserRepository;


//@RunWith(SpringRunner.class) //pas besoin car on a fait l'autowired par constructeur sur UserServiceImpl
public class UserServiceImplTest {
	
	/*
	 * La couche de service a une dépendance directe avec la couche DAO. Cependant, on n'a pas besoin de savoir comment se passe
	 * 
	 * la persistance au niveau de la couche DAO. 
	 * 
	 * Le plus important c'est que cette couche renvoie les données sollicitées. On peut donc moquer la couche DAO en utilisant le framework Mockito
	 */
 
    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    
    @Before
    public void setup() {
    	userRepository = Mockito.mock(UserRepository.class);
    	passwordEncoder = Mockito.mock(PasswordEncoder.class);
    	userService = new UserServiceImpl(userRepository, passwordEncoder);
    }
    
    @Test
    public void testFindAllUsers() throws Exception {
    	User user = new User("Dupont", "password");
    	List<User> allUsers = Arrays.asList(user);           
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
    	Collection<User> users = userService.findAllUsers();
    	assertNotNull(users);
    	assertEquals(users, allUsers);
    	assertEquals(users.size(), allUsers.size());
    	verify(userRepository).findAll();
    }
    
    @Test
    public void testSaveUser() throws Exception {
    	User user = new User("Dupont", "password");
    	User userMock = new User(1L, "Dupont", "password");
    	Mockito.when(userRepository.save((user))).thenReturn(userMock);
    	User userSaved = userService.saveOrUpdateUser(user);
    	assertNotNull(userSaved);
    	assertEquals(userMock.getId(), userSaved.getId());
     	assertEquals(userMock.getUsername(), userSaved.getUsername());
     	verify(userRepository).save(any(User.class));
    }
    
    @Test(expected=BusinessResourceException.class)
    public void testSaveUser_existing_login_throws_error() throws Exception {
    	User user = new User("Dupont", "password");
    	Mockito.when(userRepository.save((user))).thenThrow(new DataIntegrityViolationException("Duplicate Login"));
    	userService.saveOrUpdateUser(user);
     	verify(userRepository).save(any(User.class));
    }
    
    @Test
    public void testFindUserByUsername() {
    	User user = new User("Dupont", "password");
    	Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        Optional<User> userFromDB = userService.findByUsername(user.getUsername());  
        assertNotNull(userFromDB);
        assertThat(userFromDB.get().getUsername(), is(user.getUsername()));  
        verify(userRepository).findByUsername(any(String.class));
     }
    
    @Test
    public void testUpdateUser() throws Exception {
    	
    	User userToUpdate = new User(1L, "NewDupont", "newPassword");
    	
    	User userFoundById = new User(1L,"OldDupont", "oldpassword");
    	
    	User userUpdated = new User(1L, "NewDupont", "newPassword");
    	
    	
    	Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userFoundById));
    	Mockito.when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);
    	Mockito.when(passwordEncoder.encode(any(String.class))).thenReturn("newPassword");
    	Mockito.when(userRepository.save((userToUpdate))).thenReturn(userUpdated);
    	User userFromDB = userService.saveOrUpdateUser(userToUpdate);
    	assertNotNull(userFromDB);
    	verify(userRepository).save(any(User.class));
    	assertEquals(new Long(1), userFromDB.getId());
    	assertEquals("NewDupont", userFromDB.getUsername());
    	assertEquals("newPassword", userFromDB.getPassword());
    }
    
    @Test
    public void testDelete() throws Exception {
    	User userTodelete = new User(1L, "Dupont", "password");
    	Mockito.doNothing().when(userRepository).deleteById(userTodelete.getId());
    	userService.deleteUser(userTodelete.getId());
    	
    	verify(userRepository).deleteById(any(Long.class));
    }
}
