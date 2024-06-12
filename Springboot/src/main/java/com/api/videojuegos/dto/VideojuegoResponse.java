package com.api.videojuegos.dto;

import java.util.List;
import lombok.Data;

@Data
public class VideojuegoResponse {

    private Long id;
    private String nombre;
    private String genero;
    private String descripcion;
    private int anioPublicacion;
    private double precio;
    private String calificacionPorEdades;
    private String publicador;
    private String imagePath;
    private List<String> plataformas;
    

    public VideojuegoResponse(Long id, String nombre, String genero, String descripcion, int anioPublicacion, 
    double precio,String calificacionPorEdades, String publicador, String imagePath, List<String> plataformas) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.descripcion = descripcion;
        this.anioPublicacion = anioPublicacion;
        this.precio= precio;
        this.calificacionPorEdades = calificacionPorEdades;
        this.publicador = publicador;
        this.imagePath=imagePath;
        this.plataformas = plataformas;
    }
}
