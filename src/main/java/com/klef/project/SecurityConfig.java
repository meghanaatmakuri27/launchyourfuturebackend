package com.klef.project;  // Ensure this is correct

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/student/checkstudentlogin", "/student/addstudent", "/job/add", "/job/viewall", "/job/view/{id}","/job/update/{id}","/job/delete/{id}","/applications/add","/applications/viewall","/applications/","/applications/{id}","/applications/by-email/{email}","/admin/login","/admin/view","/employee/add","/employee/update/{id}","/employee/view/{id}","/employee/viewall","/employee/delete/{id}","/employee/checkemployeelogin","/applications/delete/{applicationId}").permitAll()  // Public access
                .anyRequest().authenticated()  // Require authentication for others
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Custom JWT filter
        return http.build();
    }
}
