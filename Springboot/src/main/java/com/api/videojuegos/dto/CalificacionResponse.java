package com.api.videojuegos.dto;

import lombok.Data;

@Data
public class CalificacionResponse {

    private Long id;
    private int valoracion;
    private String nombreUsuario;

    public CalificacionResponse(Long id, int valoracion, String nombreUsuario) {
        this.id = id;
        this.valoracion = valoracion;
        this.nombreUsuario = nombreUsuario;
    }
}
