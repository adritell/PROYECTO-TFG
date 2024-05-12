package com.api.videojuegos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    private boolean leido;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioDestinatario;

	

    
}
