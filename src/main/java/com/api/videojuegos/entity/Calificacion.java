package com.api.videojuegos.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Calificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int valoracion; // La calificación numérica dada por el usuario
    
    // Relación con el usuario que realizó la calificación
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    // Relación con el videojuego que fue calificado
    @ManyToOne
    @JoinColumn(name = "videojuego_id")
    private Videojuegos videojuego;

	

    
}