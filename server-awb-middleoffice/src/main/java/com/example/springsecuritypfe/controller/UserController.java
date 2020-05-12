package com.example.springsecuritypfe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.springsecuritypfe.jwt.JwtTokenProvider;
import com.example.springsecuritypfe.model.Transaction;
import com.example.springsecuritypfe.model.User;
import com.example.springsecuritypfe.service.ProductService;
import com.example.springsecuritypfe.service.TransactionService;
import com.example.springsecuritypfe.service.UserService;
import com.example.springsecuritypfe.service.UserServiceImpl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class UserController { // NB: pas de logique métier dans le contrôleur, mais, uniquement l'appel des services

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;
    
	private static final Logger  logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @GetMapping("/api/user/login")
    public ResponseEntity<?> getUser(Principal principal){
		//principal = httpServletRequest.getUserPrincipal.
        if(principal == null){
            //logout will also use here so we should return ok http status.
            return ResponseEntity.ok(principal);
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        Optional<User> user = userService.findByUsername(authenticationToken.getName());
        user.get().setToken(tokenProvider.generateToken(authenticationToken));
        logger.info("le token est : " + user.get().getToken() );
        return new ResponseEntity<>(user, HttpStatus.OK);
        
    }


    @PostMapping("/api/user/purchase")
    public ResponseEntity<?> purchaseProduct(@RequestBody Transaction transaction){
        transaction.setPurchaseDate(LocalDateTime.now());
         transactionService.saveTransaction(transaction);
         return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/api/user/products")
    public ResponseEntity<?> getAllProducts(){
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }
}

