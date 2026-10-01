package com.examp.security;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    AuthTokenFilter authTokenFilter;

    //private HttpSecurity http;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        //HttpSecurity http;
        //without form ke security chahiye to httpBasic use kar do

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest ->
                authorizeRequest.requestMatchers("/amin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/signin").permitAll()
                      //  .requestMatchers("/user/**").hasRole("USER")
                                .anyRequest().authenticated());
//                authorizeRequest.anyRequest().authenticated());
       // HttpSecurity http = null;
      //  http.httpBasic(Customizer.withDefaults());    agar signin publicily karwana hai to webpage ko disable kar do

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails user1= User.withUsername("user1")
                //.password("{noop}password1")      //as plain text password
                .password(passwordEncoder().encode("password1"))
                .roles("USER")
                .build();

        UserDetails user2= User.withUsername("user2")
                //.password("{noop}password2")        //as plain text password

                .password(passwordEncoder().encode("password2"))
                .roles("USER")      //noted as ROLE_USER
                .build();

        UserDetails admin= User.withUsername("admin")
                //.password("{noop}adminPassword")        //as plain text password
                .password(passwordEncoder().encode("adminPassword"))
                .roles("ADMIN")      //noted as ROLE_ADMIN
                .build();

       // return new InMemoryUserDetailsManager(user1,user2,admin);

//        JdbcUserDetailsManager userDetailsManager=new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.createUser(user1);
//        userDetailsManager.createUser(user2);
//        userDetailsManager.createUser(admin);
//        return userDetailsManager;

   //for exisiting ke liye
        JdbcUserDetailsManager userDetailsManager =
                new JdbcUserDetailsManager(dataSource);

        if (!userDetailsManager.userExists("user1")) {
            userDetailsManager.createUser(user1);
        }

        if (!userDetailsManager.userExists("user2")) {
            userDetailsManager.createUser(user2);
        }

        if (!userDetailsManager.userExists("admin")) {
            userDetailsManager.createUser(admin);
        }

        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder){
        return builder.getAuthenticationManager();
    }





}
