package com.api.videojuegos.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Seguidores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "seguido_id")
    private Usuario seguido;

    // Getters y setters
}

