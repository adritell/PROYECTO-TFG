package com.api.videojuegos.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

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
    @OneToMany(mappedBy = "videojuegos")
    private Set<Comentario> comentarios = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "videojuego_usuario_favorito",
        joinColumns = @JoinColumn(name = "videojuego_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuariosFavoritos = new HashSet<>(); // Nueva relación con usuarios favoritos
    
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getAnioPublicacion() {
		return anioPublicacion;
	}
	public void setAnioPublicacion(int anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}
	public String getCalificacionPorEdades() {
		return calificacionPorEdades;
	}
	public void setCalificacionPorEdades(String calificacionPorEdades) {
		this.calificacionPorEdades = calificacionPorEdades;
	}
	public String getPublicador() {
		return publicador;
	}
	public void setPublicador(String publicador) {
		this.publicador = publicador;
	}
	public String getPlataforma() {
		return plataforma;
	}
	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}
	public Set<Calificacion> getCalificaciones() {
		return calificaciones;
	}
	public void setCalificaciones(Set<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}
	public Set<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	public Set<Usuario> getUsuariosFavoritos() {
		return usuariosFavoritos;
	}
	public void setUsuariosFavoritos(Set<Usuario> usuariosFavoritos) {
		this.usuariosFavoritos = usuariosFavoritos;
	}
	
	
    
    
}