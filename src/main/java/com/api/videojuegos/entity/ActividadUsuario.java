package com.api.videojuegos.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ActividadUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Por ejemplo: "Comentario", "Juego Jugado", etc.
    private String tipoActividad; 

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    private Comentario comentario; 

    @ManyToOne
    @JoinColumn(name = "juego_id")
    private Videojuegos juego; 

    @ManyToOne
    @JoinColumn(name = "seguidor_id")
    private Seguidores seguidor; 

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    
    


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public Videojuegos getJuego() {
		return juego;
	}

	public void setJuego(Videojuegos juego) {
		this.juego = juego;
	}

	public Seguidores getSeguidor() {
		return seguidor;
	}

	public void setSeguidor(Seguidores seguidor) {
		this.seguidor = seguidor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

    
    
    
        
}