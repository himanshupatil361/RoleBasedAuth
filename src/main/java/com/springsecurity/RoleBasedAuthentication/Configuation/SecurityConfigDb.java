package com.springsecurity.RoleBasedAuthentication.Configuation;


// Authentication from database usename n pass

import com.springsecurity.RoleBasedAuthentication.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigDb {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(customizer -> customizer.disable()) // to disable csrf token  use

                .authorizeHttpRequests(request -> request

                      //  for first time main admin creation
//                        .requestMatchers("/create-admin").permitAll()

                        // after first time to create another admin
                        .requestMatchers("/create-admin").hasRole("ADMIN")

                        // this url can be only accessed by role teacher
                        .requestMatchers("/teacher/**")
                        .hasRole("TEACHER")

                        // this url can be only accessed by role student
                        .requestMatchers("/student/**")
                        .hasRole("STUDENT")

                        .anyRequest().authenticated())

                .formLogin(Customizer.withDefaults()) // for browser use this or sessionManagement

                .httpBasic(Customizer.withDefaults()); // for postman (checking api)


        return http.build(); // OR .build();
    }


    // Bcrypt pass

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider(myUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder()); // from service layer
        return provider;

    }


}
