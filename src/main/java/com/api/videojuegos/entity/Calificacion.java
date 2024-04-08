package com.api.videojuegos.entity;

import jakarta.persistence.*;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Videojuegos getVideojuego() {
		return videojuego;
	}

	public void setVideojuego(Videojuegos videojuego) {
		this.videojuego = videojuego;
	}
    
    

    
}