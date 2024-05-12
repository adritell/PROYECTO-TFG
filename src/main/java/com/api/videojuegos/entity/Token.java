package com.api.videojuegos.entity;


import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Usuario user;

    @Column(nullable = false, unique = true)
    private String token;
    

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date expirationDate; // Agregar campo de fecha de expiración

	
	
	

    

}