package com.api.videojuegos.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Videojuegos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "anio_publicacion", nullable = false)
    private int anioPublicacion;
    
    @Column(name="precio", nullable=false)
    private double precio;

    @Column(name = "calificacion_por_edades", nullable = false)
    private String calificacionPorEdades;

    @Column(nullable = false)
    private String publicador;
    
    @Column(name="imagePath", nullable=false)
    private String imagePath;

    @ElementCollection
    @CollectionTable(name = "videojuego_plataforma", joinColumns = @JoinColumn(name = "videojuego_id"))
    @Column(name = "plataforma",  nullable = false)
    private List<String> plataformas;
    
    
    // Relación con las calificaciones de los usuarios
    @OneToMany(mappedBy = "videojuego")
    private Set<Calificacion> calificaciones = new HashSet<>();
    
    
    // Relación con la tabla de comentarios
    @JsonManagedReference
    @OneToMany(mappedBy = "videojuegos", cascade = CascadeType.ALL, orphanRemoval = true)
    //@OneToMany(mappedBy = "videojuegos")
    private Set<Comentario> comentarios = new HashSet<>();
    
    

    @ManyToMany(mappedBy = "videojuegosFavoritos") 
    private Set<Usuario> usuariosFavoritos = new HashSet<>();
    
    
}