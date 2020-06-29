package com.example.springsecuritypfe.model;


import javax.persistence.*;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="username", unique = true)
    private String username;
    
    @Column(name="email")
    @Email
    private String email;

    @Column(name="password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;

	//Not persistent. There is no column on database table.
    @Transient
    private String token;
	
	public AppUser(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public AppUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public AppUser(String username,String name,Role role, String password) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role ; 
	}
    
}


