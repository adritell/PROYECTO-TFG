package com.api.videojuegos.entity;

import java.util.HashSet;
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

    @Column(name = "calificacion_por_edades", nullable = false)
    private String calificacionPorEdades;

    @Column(nullable = false)
    private String publicador;

    @Column(nullable = false)
    private String plataforma;
    
    
    // Relación con las calificaciones de los usuarios
    @OneToMany(mappedBy = "videojuego")
    private Set<Calificacion> calificaciones = new HashSet<>();
    
    
    // Relación con la tabla de comentarios
    @JsonManagedReference
    @OneToMany(mappedBy = "videojuegos")
    private Set<Comentario> comentarios = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "videojuego_usuario_favorito",
        joinColumns = @JoinColumn(name = "videojuego_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuariosFavoritos = new HashSet<>(); // Nueva relación con usuarios favoritos
    
    
    
	
	
	
    
    
}