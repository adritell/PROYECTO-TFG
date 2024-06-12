package com.api.videojuegos.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.videojuegos.entity.Rol;
import com.api.videojuegos.service.UsuarioService;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration{
	 @Autowired
	    JwtAuthenticationFilter jwtAuthenticationFilter;
	    @Autowired
	    UsuarioService usuarioService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
            .cors(cors -> {})
                    .authorizeHttpRequests(request ->
                            request
                                    .requestMatchers("api/v1/auth/**").permitAll().
                                    requestMatchers(HttpMethod.GET, "/api/v1/auth**", "/api/v1/comentarios**").hasAnyAuthority(Rol.ROLE_USER.toString(), Rol.ROLE_ADMIN.toString())
                                    .requestMatchers(HttpMethod.POST, "/api/v1/comentarios**").hasAnyAuthority(Rol.ROLE_USER.toString(), Rol.ROLE_ADMIN.toString())
                                    .requestMatchers(HttpMethod.POST, "/api/v1/usuario**", "/api/v1/videojuegos**").hasAuthority(Rol.ROLE_ADMIN.toString())
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/videojuegos/**").hasAuthority(Rol.ROLE_ADMIN.toString())
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/videojuegos/**").hasAuthority(Rol.ROLE_ADMIN.toString())
                                    .anyRequest().authenticated())
                    .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                    .authenticationProvider(authenticationProvider()).addFilterBefore(
                    jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        return http.build();
	    }

	    @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(usuarioService.userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }

	    @Bean
	    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	}