package com.api.videojuegos.repository;

import com.api.videojuegos.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByVideojuegoId(Long videojuegoId);
}
