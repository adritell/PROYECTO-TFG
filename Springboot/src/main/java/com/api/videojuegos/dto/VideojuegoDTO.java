package com.api.videojuegos.dto;

import java.util.List;

import lombok.Data;

@Data
public class VideojuegoDTO {

	private Long id;
    private String nombre;
    private String genero;
    private String descripcion;
    private int anioPublicacion;
    private double precio;
    private String calificacionPorEdades;
    private String publicador;
    private List<String> plataformas;
    private String imagePath; 

    public VideojuegoDTO(Long id, String nombre, String genero, String descripcion, int anioPublicacion, double precio,
    String calificacionPorEdades, String publicador, List<String> plataformas, String imagePath) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.descripcion = descripcion;
        this.anioPublicacion = anioPublicacion;
        this.precio=precio;
        this.calificacionPorEdades = calificacionPorEdades;
        this.publicador = publicador;
        this.imagePath = imagePath; 
        this.plataformas = plataformas;
        
    }
}
