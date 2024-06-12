package com.api.videojuegos.service;


import java.time.Instant;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.api.videojuegos.entity.Rol;
import com.api.videojuegos.entity.Usuario;


public interface JwtService {
    
    String extractUserName(String token);
        
    
    boolean isTokenValid(String token, UserDetails userDetails);
    
    String createToken(Usuario user, Instant expirationInstant, Set<Rol> roles, String nombreUsuario);
    
}
