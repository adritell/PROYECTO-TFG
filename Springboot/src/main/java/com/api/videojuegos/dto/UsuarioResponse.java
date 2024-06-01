package com.api.videojuegos.dto;

import lombok.Data;

@Data
public class UsuarioResponse {

    private String nombre;
    private String email;

    public UsuarioResponse(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

   
}
