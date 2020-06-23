package com.example.springsecuritypfe.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.springsecuritypfe.security.JWTAuthenticationFilter;
import com.example.springsecuritypfe.security.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	/*http.cors();*/
    	http.csrf().disable(); //Cross side request forgery
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                //These are public paths
                .antMatchers("/resources/**",  "/error", "/api/user/**", "/login/**", "/coursbbe" , "/tmpjj", "/courbebdt/**", "/download/**").permitAll()
                //These can be reachable for just have admin role.
                .antMatchers("/api/admin/**").hasAuthority("ADMIN")
                //All remaining paths should need authentication.
                .anyRequest().fullyAuthenticated();
        
        //Add Filters
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        //jwt filter
        //http.addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtTokenProvider));
    }


}

