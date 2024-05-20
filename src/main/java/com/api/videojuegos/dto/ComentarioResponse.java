package com.api.videojuegos.dto;

import lombok.Data;

@Data
public class ComentarioResponse {

    private Long id;
    private String text;
    private UsuarioResponse usuario;

    public ComentarioResponse(Long id, String text, UsuarioResponse usuario) {
        this.id = id;
        this.text = text;
        this.usuario = usuario;
    }
}