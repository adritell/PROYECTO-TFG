package com.api.videojuegos.service;

import com.api.videojuegos.entity.Videojuegos;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideojuegosService {
    
    List<Videojuegos> getAllVideojuegos();
    
    Optional<Videojuegos> getVideojuegoById(Long id);
    
    Videojuegos createVideojuego(Videojuegos videojuego);
    
    Videojuegos updateVideojuego(Long id, Videojuegos videojuego);
    
    boolean deleteVideojuego(Long id);
    
    List<Videojuegos> searchVideojuegos(String keyword);
    
    Optional<Videojuegos> findByNombre(String nombre);
    
    Page<Videojuegos> getVideojuegosPaginados(Pageable pageable);
}
