package com.api.videojuegos.dto;

import lombok.Data;

@Data
public class UsuarioResponse {

	private Long id;
    private String nombre;
    private String email;

    public UsuarioResponse(Long id,String nombre, String email) {
    	this.id=id;
        this.nombre = nombre;
        this.email = email;
    }

   
}
