package com.example.springsecuritypfe.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springsecuritypfe.model.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
    private AuthenticationManager authenticationManager;
    //private UserRepository userRepository ;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AppUser u= new ObjectMapper().readValue(request.getInputStream(),AppUser.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(u.getUsername(),u.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
    	
        User user=(User)authResult.getPrincipal();  /* la classe User propre à Spring Security*/
        //AppUser u = userRepository.findByUsername(user.getUsername()).orElse(null);
        List<String> roles=new ArrayList<>();
        authResult.getAuthorities().forEach(a->{ roles.add(a.getAuthority());});
        String jwt= JWT.create()
                .withIssuer(request.getRequestURI())
                .withSubject(user.getUsername())
                //.withClaim("fullname",u.getName())
                .withArrayClaim("roles",roles.toArray(new String[roles.size()]))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityParams.EXPIRATION))
                .sign(Algorithm.HMAC256(SecurityParams.SECRET));
                
        log.info("L'utilisateur "+user.getUsername().toUpperCase()+" possédant le role "+roles.get(0)+" est connecté." );

        response.addHeader(SecurityParams.JWT_HEADER_NAME,jwt);
    }

}
