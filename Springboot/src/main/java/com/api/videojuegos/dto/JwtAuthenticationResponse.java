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
