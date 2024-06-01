package com.api.videojuegos.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ComentarioResponse {

    private Long id;
    private String text;
    private UsuarioResponse usuario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha; 

    public ComentarioResponse(Long id, String text, UsuarioResponse usuario, LocalDateTime fecha) {
        this.id = id;
        this.text = text;
        this.usuario = usuario;
        this.fecha = fecha;
    }
}