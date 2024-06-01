package com.api.videojuegos.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.api.videojuegos.entity.Rol;

public class JwtAuthenticationResponse {

    private String token;
    /*private String nombre;
    private Set<Rol> roles;
    private LocalDateTime expirationDate;*/

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

   /* public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }*/

    public static JwtAuthenticationResponseBuilder builder() {
        return new JwtAuthenticationResponseBuilder();
    }

    public static class JwtAuthenticationResponseBuilder {
        private String token;

        public JwtAuthenticationResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public JwtAuthenticationResponse build() {
            return new JwtAuthenticationResponse(token);
        }
    }
}
