package com.api.videojuegos.service;

import com.api.videojuegos.entity.Calificacion;
import java.util.List;

public interface CalificacionService {
    List<Calificacion> getCalificacionesByVideojuegoId(Long videojuegoId);
}
